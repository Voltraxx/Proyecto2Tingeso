import repairService from '../services/repair.service'
import { useState, useEffect } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell, { tableCellClasses } from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import { Box } from '@mui/material';
import Paper from "@mui/material/Paper";
import Button from "@mui/material/Button";
import PersonAddIcon from "@mui/icons-material/PersonAdd";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import { Add, Info, Settings } from "@mui/icons-material";

const RepairList = () => {
  const { id } = useParams();
  const [repairs, setRepairs] = useState([]);

  const navigate = useNavigate();

  const init = () => {
    repairService
      .getRepairsByCar(id)
      .then((response) => {
        console.log("Mostrando listado de todos las reparaciones.", response.data);
        setRepairs(response.data);
      })
      .catch((error) => {
        console.log(
          "Se ha producido un error al intentar mostrar listado de todos las reparaciones.",
          error
        );
      });
  };

  useEffect(() => {
    init();
  }, []);

  const handleAdd = (id) => {
    console.log("Printing id", id);
    navigate(`/repair/add/${id}`);
  };

  const handleEdit = (id) => {
    console.log("Printing id", id);
    navigate(`/repair/edit/${id}`);
  };

  const handleDelete = (id) => {
    console.log("Printing id", id);
    const confirmDelete = window.confirm(
      "¿Esta seguro que desea borrar esta reparación?"
    );
    if (confirmDelete) {
      repairService
        .remove(id)
        .then((response) => {
          console.log("Reparación ha sido eliminada.", response.data);
          init();
        })
        .catch((error) => {
          console.log(
            "Se ha producido un error al intentar eliminar la reparación",
            error
          );
        });
    }
  };

  return (
    
    <TableContainer sx={{ marginLeft:"-9rem",width: "97rem" ,mt: "2rem", mb: "2rem"}}
      component={Paper}>
      <br />
      
      <Button
        variant="contained"
        color="primary"
        startIcon={<PersonAddIcon />}
        onClick={() => handleAdd(id)}
        sx={{ backgroundColor: "#632654" }}
      >
        Agregar Reparación
      </Button>
      
      <br /> <br />

      {repairs.length > 0 ? (

        <Table sx={{width: "100%" }} size="small" aria-label="a dense table">
          <TableHead>
            <TableRow>
              <TableCell align="left" sx={{ fontWeight: "bold", fontSize: 13 }}>
                ID
              </TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold", fontSize: 13 }}>
                Fecha Ingreso
              </TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold", fontSize: 13 }}>
                Hora Ingreso
              </TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold", fontSize: 13 }}>
                Costo Total
              </TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold", fontSize: 13 }}>
                Fecha Salida
              </TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold", fontSize: 13 }}>
                Hora Salida
              </TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold", fontSize: 13 }}>
                Fecha Despacho
              </TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold", fontSize: 13 }}>
                Hora Despacho
              </TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold", fontSize: 13 }}>
                Desc.Reps.
              </TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold", fontSize: 13 }}>
                Desc.Dia
              </TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold", fontSize: 13 }}>
                Desc.Bono
              </TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold", fontSize: 13 }}>
                Rec.Kms
              </TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold", fontSize: 13 }}>
                Rec.Años
              </TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold", fontSize: 13 }}>
                Rec.Delay
              </TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold", fontSize: 13 }}>
                IVA
              </TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold", fontSize: 13 }}>
                Operaciones
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {repairs.map((repair) => (
              <TableRow
                key={repair.id}
                sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
              >
                <TableCell align="left">{repair.id}</TableCell>
                <TableCell align="right">{repair.fechaIngreso}</TableCell>
                <TableCell align="right">{repair.horaIngreso}</TableCell>
                <TableCell align="right">${repair.montoTotal}</TableCell>
                <TableCell align="right">{repair.fechaSalida ? repair.fechaSalida: "Pendiente"}</TableCell>
                <TableCell align="right">{repair.horaSalida ? repair.horaSalida: "Pendiente"}</TableCell>
                <TableCell align="right">{repair.fechaDespacho ? repair.fechaDespacho: "Pendiente"}</TableCell>
                <TableCell align="right">{repair.horaDespacho ? repair.horaDespacho: "Pendiente"}</TableCell>
                <TableCell align="right">${repair.repairDiscount}</TableCell>
                <TableCell align="right">${repair.dayDiscount}</TableCell>
                <TableCell align="right">${repair.bonusDiscount}</TableCell>
                <TableCell align="right">${repair.kmCharge}</TableCell>
                <TableCell align="right">${repair.ageCharge}</TableCell>
                <TableCell align="right">{repair.delayCharge ? "$" + repair.delayCharge: "Pendiente"}</TableCell>
                <TableCell align="right">{repair.ivaCharge ? "$" + repair.ivaCharge: "Pendiente"}</TableCell>
                
                <TableCell>
                  <Button
                    variant="contained"
                    color="info"
                    size="small"
                    sx={{ backgroundColor: "#632654" }}
                    onClick={() => handleEdit(repair.id)}
                    style={{ marginLeft: "0.5rem" }}
                    startIcon={<EditIcon />}
                  >
                    Editar
                  </Button>

                  <Button
                    variant="contained"
                    color="info"
                    size="small"
                    sx={{ backgroundColor: "#632654" }}
                    onClick={() => navigate(`/repair/${repair.id}`)}
                    style={{ marginLeft: "0.5rem" }}
                    startIcon={<Settings />}
                  >
                    Detalles
                  </Button>

                  <Button
                    variant="contained"
                    color="error"
                    size="small"
                    onClick={() => handleDelete(repair.id)}
                    style={{ marginLeft: "0.5rem" }}
                    startIcon={<DeleteIcon />}
                  >
                    Eliminar
                  </Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>

      ) : (
        <Box sx={{ p: 2 }}>
            No hay reparaciones para este vehículo.
        </Box>
      )
    }
    </TableContainer>
  );


};

export default RepairList;
