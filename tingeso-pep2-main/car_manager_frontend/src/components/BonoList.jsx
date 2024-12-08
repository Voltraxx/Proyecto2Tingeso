import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import bonoService from '../services/bono.service'
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import Button from "@mui/material/Button";
import PersonAddIcon from "@mui/icons-material/PersonAdd";
import DeleteIcon from "@mui/icons-material/Delete";
import { Typography, Box} from "@mui/material";

const BonoList = () => {
  const [bonos, setBonos] = useState([]);
  const navigate = useNavigate();

  const init = () => {
    bonoService
      .getAll()
      .then((response) => {
        console.log("Mostrando listado de todos los bonos.", response.data);
        setBonos(response.data);
      })
      .catch((error) => {
        console.log(
          "Se ha producido un error al intentar mostrar listado de todos los bonos.",
          error
        );
      });
  };

  useEffect(() => {
    init();
  }, []);

  const handleDelete = (id) => {
    console.log("Printing id", id);
    const confirmDelete = window.confirm(
      "¿Esta seguro que desea borrar este bono?"
    );
    if (confirmDelete) {
      bonoService
        .remove(id)
        .then((response) => {
          console.log("Bono ha sido eliminado.", response.data);
          init();
        })
        .catch((error) => {
          console.log(
            "Se ha producido un error al intentar eliminar al bono",
            error
          );
        });
    }
  };

  return (
    <Box align="center" mt="4rem">
      <Typography sx={{ fontWeight: "bold"}} variant="h4" gutterBottom>
        Bonos Registrados
      </Typography>
      <TableContainer sx={{ maxWidth: 600 , mb:"2rem" }} 
      component={Paper}>
        <br />
        <Link
          to="/bono/add"
          style={{ textDecoration: "none", marginBottom: "1rem" }}
        >
          <Button
            variant="contained"
            color="primary"
            startIcon={<PersonAddIcon />}
            sx={{ backgroundColor: "#632654" }}
          >
            Añadir Bono
          </Button>
        </Link>
        <br /> <br />
        <Table size="small" aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>
                ID
              </TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>
                Marca
              </TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold" }}>
                Monto
              </TableCell>
              <TableCell align="center" sx={{ fontWeight: "bold" }}>
                Operaciones
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {bonos.map((bono) => (
              <TableRow
                key={bono.id}
                sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
              >
                <TableCell align="left">{bono.id}</TableCell>
                <TableCell align="left">{bono.marca}</TableCell>
                <TableCell align="right">${bono.monto}</TableCell>
                <TableCell align="center"> 
                  <Button
                    variant="contained"
                    color="error"
                    size="small"
                    onClick={() => handleDelete(bono.id)}
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
      </TableContainer>
    </Box>
  );
};

export default BonoList;