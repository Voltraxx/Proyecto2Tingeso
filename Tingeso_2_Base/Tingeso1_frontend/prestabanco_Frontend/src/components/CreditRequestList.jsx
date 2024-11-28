import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import creditRequestService from "../services/credit.request.service";
import TextField from "@mui/material/TextField";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import Button from "@mui/material/Button";
import Box from "@mui/material/Box";
import PersonAddIcon from "@mui/icons-material/PersonAdd";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import CalculateIcon from "@mui/icons-material/Calculate";
import CancelIcon from "@mui/icons-material/Cancel"
import AccountBalanceIcon from "@mui/icons-material/AccountBalance";
import Collapse from "@mui/material/Collapse";

const CreditRequestList = () => {
  const [creditRequests, setCreditRequests] = useState([]);
  const [expandedRow, setExpandedRow] = useState(null); // Estado para la fila expandida
  const [costDetails, setCostDetails] = useState({}); // Estado para almacenar los datos de costo total
  const [formValues, setFormValues] = useState({
      seguroDesgravamen: '',
      seguroIncendio: '',
      comisionAdministracion: ''
  });

  const navigate = useNavigate();

  const init = () => {
    creditRequestService
      .getAll()
      .then((response) => {
        console.log("Mostrando listado de todas las solicitudes de crédito.", response.data);
        setCreditRequests(response.data);
      })
      .catch((error) => {
        console.log(
          "Se ha producido un error al intentar mostrar el listado de todas las solicitudes de crédito.",
          error
        );
      });
  };

  useEffect(() => {
    init();
  }, []);

  const handleDelete = (id) => {
    const confirmDelete = window.confirm("¿Está seguro que desea borrar esta solicitud?");
    if (confirmDelete) {
      creditRequestService
        .remove(id)
        .then(() => {
          init();
        })
        .catch((error) => {
          console.log("Error al eliminar la solicitud", error);
        });
    }
  };

  const handleCalculateCost = async (id) => {
    try {
      const response = await creditRequestService.calculateTotalCost(id, {
        seguroDesgravamen: parseFloat(formValues.seguroDesgravamen),
        seguroIncendio: parseFloat(formValues.seguroIncendio),
        comisionAdministracion: parseFloat(formValues.comisionAdministracion)
      });
      setCostDetails((prevDetails) => ({
        ...prevDetails,
        [id]: response.data,
      }));
    } catch (error) {
      console.error("Error al obtener el costo total:", error);
    }
  };
  

  const handleEdit = (id) => {
    navigate(`/creditRequest/edit/${id}`);
  };

  // const handleEvaluate = (id) => {
  //   navigate(`/creditRequest/evaluate/${id}`);
  // };

  const handleToggleExpand = async (id) => {
    setExpandedRow(expandedRow === id ? null : id);
    if (expandedRow !== id && formValues.seguroDesgravamen && formValues.seguroIncendio && formValues.comisionAdministracion) {
      try {
        const response = await creditRequestService.calculateTotalCost(id, formValues);
        setCostDetails((prevDetails) => ({
          ...prevDetails,
          [id]: response.data,
        }));
      } catch (error) {
        console.error("Error al obtener el costo total:", error);
      }
    }
  };

  const handleInputChange = (e) => {
    setFormValues({ ...formValues, [e.target.name]: e.target.value });
  };

  const handleEvaluate = async (id) => {
      try {
          await creditRequestService.updateStatus(id, "evaluate");
          navigate(`/creditRequest/evaluate/${id}`);
      } catch (error) {
          console.error("Error al actualizar el estado a 'En Evaluación'", error);
      }
  };

  const handleCancel = async (id) => {
      try {
          await creditRequestService.updateStatus(id, "cancel");
          init(); // Actualizar la lista de solicitudes para reflejar el cambio de estado
      } catch (error) {
          console.error("Error al actualizar el estado a 'Cancelado por el Cliente'", error);
      }
  };

  const handleApprove = async (id) => {
      try {
          await creditRequestService.updateStatus(id, "moveToFinalApproval");
          init(); 
      } catch (error) {
          console.error("Error al cambiar el estado a 'Aprobación Final'", error);
      }
  };

  const handleFinalApproval = async (id) => {
      try {
        await creditRequestService.updateStatus(id, "finalApproval");
        init();
      } catch (error) {
        console.error("Error al realizar la aprobación final", error);
      }
  };

  const handleDisburse = async (id) => {
      try {
          await creditRequestService.updateStatus(id, "disburseLoan");
          init(); 
      } catch (error) {
          console.error("Error al cambiar el estado a 'En Desembolso'", error);
      }
  };


  return (
    <TableContainer component={Paper}>
      <br />
      <Link to="/creditRequest/add" style={{ textDecoration: "none", marginBottom: "1rem" }}>
        <Button variant="contained" color="primary" startIcon={<PersonAddIcon />}>
          Añadir solicitud de crédito
        </Button>
      </Link>
      <br /> <br />
      <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
        <TableHead>
          <TableRow>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Nombre del usuario</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Tipo de Préstamo</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Plazo de pago (años)</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Tasa de interés (%)</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Valor de la propiedad ($)</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Valor del préstamo ($)</TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>Cuota mensual ($)</TableCell>
            <TableCell align="right" sx={{ fontWeight: "bold" }}>Estado</TableCell>
            <TableCell align="center" sx={{ fontWeight: "bold" }}>Acciones</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {creditRequests.map((creditRequest) => (
            <React.Fragment key={creditRequest.id}>
              <TableRow sx={{ "&:last-child td, &:last-child th": { border: 0 } }}>
                <TableCell align="left">{creditRequest.user.name}</TableCell>
                <TableCell align="left">{creditRequest.type}</TableCell>
                <TableCell align="left">{creditRequest.term}</TableCell>
                <TableCell align="left">{creditRequest.interest}</TableCell>
                <TableCell align="left">{creditRequest.propertyValue}</TableCell>
                <TableCell align="left">{creditRequest.loanValue}</TableCell>
                <TableCell align="left">{creditRequest.monthlyQuota}</TableCell>
                <TableCell align="right">{creditRequest.status}</TableCell>
                <TableCell>
                  <Button
                    variant="contained"
                    color="info"
                    size="small"
                    onClick={() => handleEdit(creditRequest.id)}
                    style={{ marginLeft: "0.5rem" }}
                    startIcon={<EditIcon />}
                  >
                    Editar
                  </Button>
                  <Button
                    variant="contained"
                    color="error"
                    size="small"
                    onClick={() => handleDelete(creditRequest.id)}
                    style={{ marginLeft: "0.5rem" }}
                    startIcon={<DeleteIcon />}
                  >
                    Eliminar
                  </Button>
                  <Button
                    variant="contained"
                    color="success"
                    size="small"
                    onClick={() => handleEvaluate(creditRequest.id)}
                    style={{ marginLeft: "0.5rem" }}
                    startIcon={<AccountBalanceIcon />}
                  >
                    Evaluar
                  </Button>
                  <Button
                    variant="contained"
                    color="warning"
                    size="small"
                    onClick={() => handleCancel(creditRequest.id)}
                    style={{ marginLeft: "0.5rem" }}
                    startIcon={<CancelIcon />}
                  >
                    Cancelar
                  </Button>
                  {creditRequest.status === "Pre-Aprobada" && (
                    <Button
                      variant="contained"
                      color="primary"
                      size="small"
                      onClick={() => handleApprove(creditRequest.id)}
                      style={{ marginLeft: "0.5rem" }}
                    >
                      Aprobar
                    </Button>
                  )}
                  {creditRequest.status === "En Aprobación Final" && (
                    <Button
                      variant="contained"
                      color="primary"
                      size="small"
                      onClick={() => handleFinalApproval(creditRequest.id)}
                      style={{ marginLeft: "0.5rem" }}
                    >
                      Aprobación Final
                    </Button>
                  )}
                  {creditRequest.status === "Aprobada" && (
                    <Button
                      variant="contained"
                      color="primary"
                      size="small"
                      onClick={() => handleDisburse(creditRequest.id)}
                      style={{ marginLeft: "0.5rem" }}
                    >
                      Desembolsar
                    </Button>
                  )}
                    <Button
                      variant="contained"
                      color="secondary"
                      onClick={() => handleToggleExpand(creditRequest.id)}
                      style={{ marginLeft: "1rem", marginTop: "1rem" }}
                      startIcon={<CalculateIcon />}
                    >
                      Calcular Costo Total
                    </Button>
                </TableCell>
              </TableRow>
              {/* Fila expandida */}
              <TableRow>
                <TableCell colSpan={9} style={{ paddingBottom: 0, paddingTop: 0 }}>
                  <Collapse in={expandedRow === creditRequest.id} timeout="auto" unmountOnExit>
                    <Box margin={1}>
                      <h5>Ingresar Valores para Costo Total del Préstamo</h5>
                      
                      {/* Inputs para valores personalizados */}
                      <TextField
                        label="Seguro de Desgravamen"
                        variant="outlined"
                        name="seguroDesgravamen"
                        value={formValues.seguroDesgravamen}
                        onChange={handleInputChange}
                        type="number"
                        style={{ marginRight: "1rem", marginBottom: "1rem" }}
                        InputProps={{ inputProps: { min: 0 } }}
                      />
                      <TextField
                        label="Seguro de Incendio"
                        variant="outlined"
                        name="seguroIncendio"
                        value={formValues.seguroIncendio}
                        onChange={handleInputChange}
                        type="number"
                        style={{ marginRight: "1rem", marginBottom: "1rem" }}
                        InputProps={{ inputProps: { min: 0 } }}
                      />
                      <TextField
                        label="Comisión por Administración"
                        variant="outlined"
                        name="comisionAdministracion"
                        value={formValues.comisionAdministracion}
                        onChange={handleInputChange}
                        type="number"
                        style={{ marginBottom: "1rem" }}
                        InputProps={{ inputProps: { min: 0 } }}
                      />
                      
                      <Button
                        variant="contained"
                        color="secondary"
                        onClick={() => handleCalculateCost(creditRequest.id)}
                        style={{ marginLeft: "1rem", marginTop: "1rem" }}
                      >
                        Calcular Costo Total
                      </Button>

                      {/* Mostrar resultados */}
                      {costDetails[creditRequest.id] ? (
                        <div>
                          <h5>Detalle del Costo Total</h5>
                          <p><strong>Cuota Mensual:</strong> ${costDetails[creditRequest.id].cuotaMensual.toFixed(2)}</p>
                          <p>
                            <strong>Seguro de Desgravamen:</strong> ${costDetails[creditRequest.id].seguroDesgravamen.toFixed(2)}
                          </p>
                          <p>
                            <strong>Seguro de Incendio:</strong> ${costDetails[creditRequest.id].seguroIncendio.toFixed(2)}
                          </p>
                          <p>
                            <strong>Total de Seguros:</strong> ${costDetails[creditRequest.id].totalSeguros.toFixed(2)}
                          </p>
                          <p>
                            <strong>Comisión por Administración:</strong> ${costDetails[creditRequest.id].comisionAdministracion.toFixed(2)}
                          </p>
                          <p>
                            <strong>Costo Mensual Total:</strong> ${costDetails[creditRequest.id].costoMensual.toFixed(2)}
                          </p>
                          <p>
                            <strong>Costo Total del Préstamo:</strong> ${costDetails[creditRequest.id].costoTotal.toFixed(2)}
                          </p>
                        </div>
                      ) : (
                        <p>Cargando detalles de costo...</p>
                      )}
                    </Box>
                  </Collapse>
                </TableCell>
              </TableRow>

            </React.Fragment>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default CreditRequestList;
