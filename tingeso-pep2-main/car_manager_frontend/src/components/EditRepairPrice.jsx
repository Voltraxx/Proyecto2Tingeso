import { useState, useEffect } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import repairpriceService from "../services/repairprice.service";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import FormControl from "@mui/material/FormControl";
import MenuItem from "@mui/material/MenuItem";
import SaveIcon from "@mui/icons-material/Save";

const EditRepairPrice = () => {
    const { id } = useParams();

    const [type,setType] = useState("");
    const [montoG,setMontoG] = useState(0);
    const [montoD,setMontoD] = useState(0);
    const [montoH,setMontoH] = useState(0);
    const [montoE,setMontoE] = useState(0);

    const navigate = useNavigate();

    const saveRprice = (e) => {
        e.preventDefault();
    
        // Validación de campos vacíos
        if (!type || !montoG || !montoD || !montoH || !montoE) {
          alert("Por favor complete todos los campos.");
          return;
        }
    
        const rprice = { id, type, montoG, montoD, montoH, montoE };
        
        //Crear nuevo Rprice
        repairpriceService
        .update(rprice)
        .then((response) => {
            console.log("Precio de reparación ha sido actualizado.", response.data);
            navigate("/rprice/list");
        })
        .catch((error) => {
            console.log(
            "Ha ocurrido un error al intentar crear actualizar reparación.",
            error
            );
        });
    };

    useEffect(() => {
        repairpriceService
        .get(id)
        .then((rprice) => {
            setType(rprice.data.type);
            setMontoG(rprice.data.montoG);
            setMontoD(rprice.data.montoD);
            setMontoH(rprice.data.montoH);
            setMontoE(rprice.data.montoE);
        })
        .catch((error) => {
            console.log("Se ha producido un error.", error);
        });
    }, []);

    return (
        <Box
          display="flex"
          flexDirection="column"
          alignItems="center"
          justifyContent="center"
          component="form"
          padding={7}
        >
          <h3> Editar Precio de Reparación </h3>
    
          <form>
            <FormControl fullWidth>
              <TextField
                id="type"
                label="Tipo"
                value={type}
                variant="outlined"
                onChange={(e) => setType(e.target.value)}
                style={{ margin: "5px" }}
                helperText="Ej: Reparaciones del Motor"
              />
            </FormControl>
    
            <FormControl fullWidth>
              <TextField
                id="montog"
                label="Precio Gasolina"
                value={montoG}
                variant="outlined"
                onChange={(e) => setMontoG(e.target.value)}
                style={{ margin: "5px" }}
              />
            </FormControl>

            <FormControl fullWidth>
              <TextField
                id="montod"
                label="Precio Diésel"
                value={montoD}
                variant="outlined"
                onChange={(e) => setMontoD(e.target.value)}
                style={{ margin: "5px" }}
              />
            </FormControl>

            <FormControl fullWidth>
              <TextField
                id="montoh"
                label="Precio Híbrido"
                value={montoH}
                variant="outlined"
                onChange={(e) => setMontoH(e.target.value)}
                style={{ margin: "5px" }}
              />
            </FormControl>

            <FormControl fullWidth>
              <TextField
                id="montoe"
                label="Precio Eléctrico"
                value={montoE}
                variant="outlined"
                onChange={(e) => setMontoE(e.target.value)}
                style={{ margin: "5px" }}
              />
            </FormControl>
    
            <FormControl>
              <br />
              <Button
                variant="contained"
                color="info"
                onClick={(e) => saveRprice(e)}
                style={{ marginLeft: "0.5rem" }}
                startIcon={<SaveIcon />}
              >
                Guardar cambios
              </Button>
            </FormControl>
          </form>
          <br />
          <Link to="/rprice/list">Back to List</Link>
        </Box>
      );
};

export default EditRepairPrice;