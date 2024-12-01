import { useState, useEffect } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import creditRequestService from "../services/credit.request.service";
import creditSimulationService from "../services/credit.simulation.service";
import userService from "../services/user.service";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import FormControl from "@mui/material/FormControl";
import MenuItem from "@mui/material/MenuItem";
import SaveIcon from "@mui/icons-material/Save";
import { ToastContainer, toast } from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';

const AddEditCreditRequest = () => {
  const [userId, setUserId] = useState("");
  const [type, setType] = useState("");
  const [status, setStatus] = useState("");
  const [interest, setInterest] = useState("");
  const [term, setTerm] = useState("");
  const [propertyValue, setPropertyValue] = useState("");
  const [monthlyQuota, setMonthlyQuota] = useState("");
  const [documents, setDocuments] = useState({ document1: null, document2: null, document3: null, document4: null });
  const [usersList, setUsersList] = useState([]);
  const [loanConditions, setLoanConditions] = useState(null);
  const { id } = useParams();
  const [titleCreditRequestForm, setTitleCreditRequestForm] = useState("");
  const navigate = useNavigate();

  // Manejar los cambios en los archivos seleccionados
  const handleFileChange = (event, docName) => {
    const file = event.target.files[0];
    console.log(`Archivo seleccionado para ${docName}:`, file);
    setDocuments({ ...documents, [docName]: file });
  };
  
  const uploadDocuments = async (creditRequestId) => {
    const formData = new FormData();
    Object.keys(documents).forEach((key) => {
        if (documents[key]) {
            formData.append(key, documents[key]);
        }
    });
    await creditRequestService.uploadDocuments(creditRequestId, formData);
    
    // Llamar al servicio para obtener el estado actualizado
    const updatedRequest = await creditRequestService.get(creditRequestId);
    setStatus(updatedRequest.data.status);
  };

  // Función para guardar la solicitud de crédito
  const saveCreditRequest = async (e) => {
    e.preventDefault();

    const creditRequest = { 
      userId,
      type, 
      status, 
      interest, 
      term, 
      propertyValue,
      monthlyQuota,
      ...(id && { id }),
    };

    try {
      let response;
      if (id) {
        // Actualizar solicitud existente
        response = await creditRequestService.update(creditRequest);
        console.log("La solicitud de crédito ha sido actualizada.", response.data);
      } else {
        // Crear nueva solicitud de crédito
        response = await creditRequestService.create(creditRequest);
        console.log("Nueva solicitud de crédito creada.", response.data);
      }
      
      // Subir documentos después de guardar la solicitud
      if (documents.document1 || documents.document2 || documents.document3 || documents.document4) {
        await uploadDocuments(response.data.id);
      }
      
      navigate("/CreditRequestList/");
    } catch (error) {
      // Mostrar notificación si hay un error con la validación
      if (error.response && error.response.status === 400) {
        toast.error(error.response.data, {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      } else {
        console.log("Error al guardar la solicitud de crédito.", error);
      }
    }
  };

  useEffect(() => {
    if (id) {
      setTitleCreditRequestForm("Editar Solicitud de Crédito");
      creditRequestService.get(id)
        .then((creditRequest) => {
          setUserId(creditRequest.data.userId);
          setType(creditRequest.data.type);
          setStatus(creditRequest.data.status);
          setInterest(creditRequest.data.interest);
          setTerm(creditRequest.data.term);
          setPropertyValue(creditRequest.data.propertyValue);
          setMonthlyQuota(creditRequest.data.monthlyQuota);
          
          // Verificar si los documentos existen
          setDocuments({
            document1: creditRequest.data.document1 ? true : null,
            document2: creditRequest.data.document2 ? true : null,
            document3: creditRequest.data.document3 ? true : null,
            document4: creditRequest.data.document4 ? true : null,
          });
        })
        .catch((error) => console.log("Error al cargar la solicitud de crédito.", error));
    } else {
      setTitleCreditRequestForm("Nueva Solicitud de Crédito");
    }
  
    userService.getAll()
      .then((response) => setUsersList(response.data))
      .catch((error) => console.log("Error al cargar la lista de usuarios.", error));
  }, [id]);

  useEffect(() => {
    let propertyValueNew = 0; // Declarar sin especificar tipo

    if (propertyValue && interest && term && type) {
      // Realizar el cálculo en función del tipo de préstamo
      if (type === "Primera Vivienda") {
        propertyValueNew = propertyValue * 0.8;
      } else if (type === "Segunda Vivienda") {
        propertyValueNew = propertyValue * 0.7;
      } else if (type === "Propiedades Comerciales") {
        propertyValueNew = propertyValue * 0.6;
      } else if (type === "Remodelacion") {
        propertyValueNew = propertyValue * 0.5;
      }

      // Llamar al servicio con el valor calculado
      creditSimulationService.calculateCredit(propertyValueNew, interest / 12 / 100, term * 12)
        .then((response) => setMonthlyQuota(response.data))
        .catch((error) => console.error("Error al calcular la cuota:", error));
    }
  }, [propertyValue, interest, term, type]); // Añadimos `type` como dependencia
  
  const handleDownload = (docNumber) => {
    creditRequestService.downloadDocument(id, docNumber)
      .then((response) => {
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', `document${docNumber}.pdf`);
        document.body.appendChild(link);
        link.click();
        link.parentNode.removeChild(link);
      })
      .catch((error) => console.error("Error al descargar el documento:", error));
  };

  useEffect(() => {
    if (type) {
      creditRequestService.getLoanConditions(type)
        .then((response) => setLoanConditions(response.data))
        .catch(console.error);
    }
  }, [type]);

  return (
    <Box
      display="flex"
      flexDirection="column"
      alignItems="center"
      justifyContent="center"
      component="form"
      onSubmit={saveCreditRequest}
    >
      <h3>{titleCreditRequestForm}</h3>
      <hr />

      {/* Selección de Usuario */}
      <FormControl fullWidth>
        <TextField
          select
          label="Usuario"
          value={userId}
          onChange={(e) => setUserId(e.target.value)}
          variant="standard"
          InputProps={{
            style: { color: 'white' },
          }}
          InputLabelProps={{
            style: { color: 'white' },
          }}
        >
          {usersList.map((user) => (
            <MenuItem key={user.id} value={user.id}>
              {user.name}
            </MenuItem>
          ))}
        </TextField>
      </FormControl>

      {/* Selección de Préstamo */}
      <FormControl fullWidth>
        <TextField
          select
          label="Tipo de Préstamo"
          value={type}
          onChange={(e) => setType(e.target.value)}
          variant="standard"
          InputProps={{
            style: { color: 'white' },
          }}
          InputLabelProps={{
            style: { color: 'white' },
          }}
        >
          {["Primera Vivienda", "Segunda Vivienda", "Propiedades Comerciales", "Remodelación"].map((loanType) => (
            <MenuItem key={loanType} value={loanType}>{loanType}</MenuItem>
          ))}
        </TextField>
      </FormControl>

      <FormControl fullWidth>
        <TextField
          label="Interés (%)"
          type="number"
          value={interest}
          variant="standard"
          onChange={(e) => setInterest(e.target.value)}
          InputProps={{
            style: { color: 'white' },
          }}
          InputLabelProps={{
            style: { color: 'white' },
          }}
          helperText={
            loanConditions 
              ? `Rango permitido: ${loanConditions.minInterest}% - ${loanConditions.maxInterest}%`
              : ""
          }
        />
      </FormControl>

      <FormControl fullWidth>
        <TextField
          label="Plazo (años)"
          type="number"
          value={term}
          variant="standard"
          onChange={(e) => setTerm(e.target.value)}
          InputProps={{
            style: { color: 'white' },
          }}
          InputLabelProps={{
            style: { color: 'white' },
          }}
          helperText={loanConditions ? `Máximo: ${loanConditions.maxTerm} años` : ""}
        />
      </FormControl>

      <FormControl fullWidth>
        <TextField
          label="Valor de la Propiedad"
          type="number"
          value={propertyValue}
          variant="standard"
          onChange={(e) => setPropertyValue(e.target.value)}
          InputProps={{
            style: { color: 'white' },
          }}
          InputLabelProps={{
            style: { color: 'white' },
          }}
          helperText={loanConditions ? `Financiamiento: ${loanConditions.maxFunding * 100}%` : ""}
        />
      </FormControl>

      <FormControl fullWidth>
        <TextField
          label="Cuota mensual"
          type="number"
          value={monthlyQuota}
          variant="standard"
          onChange={(e) => setMonthlyQuota(e.target.value)}
          InputProps={{
            style: { color: 'white' },
          }}
          InputLabelProps={{
            style: { color: 'white' },
          }}
        />
      </FormControl>
      
      {[1, 2, 3, 4].map((num) => (
      <FormControl key={num} fullWidth style={{ marginTop: '1rem' }}>
        <label>Documento {num}:</label>
        {documents[`document${num}`] ? (
          <div>
            <Button variant="contained" color="secondary" onClick={() => handleDownload(num)}>
              Descargar documento {num}
            </Button>
            <span style={{ marginLeft: '1rem' }}>Archivo ya subido</span>
          </div>
        ) : (
          <input
            type="file"
            onChange={(e) => handleFileChange(e, `document${num}`)}
          />
        )}
        </FormControl>
      ))}

      {/* Botón Guardar */}
      <FormControl>
        <br />
        <Button
          variant="contained"
          color="info"
          type="submit"
          style={{ marginLeft: "0.5rem" }}
          startIcon={<SaveIcon />}
        >
          Guardar
        </Button>
      </FormControl>
      <hr />
      <Link to="/CreditRequestList/">Volver a la lista</Link>
      
      {/* Contenedor de notificaciones */}
      <ToastContainer />
    </Box>
  );
};

export default AddEditCreditRequest;

