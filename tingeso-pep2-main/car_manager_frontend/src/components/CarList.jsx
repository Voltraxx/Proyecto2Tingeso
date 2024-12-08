import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import carService from '../services/car.service';
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell, { tableCellClasses } from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import Button from "@mui/material/Button";
import PersonAddIcon from "@mui/icons-material/PersonAdd";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import { Add, Info, Settings } from "@mui/icons-material";
import { Typography, Box} from "@mui/material";

const CarList = () => {
  const [cars, setCars] = useState([]);
  const navigate = useNavigate();

  const init = () => {
    carService
      .getAll()
      .then((response) => {
        console.log("Mostrando listado de todos los autos.", response.data);
        setCars(response.data);
      })
      .catch((error) => {
        console.log(
          "Se ha producido un error al intentar mostrar listado de todos los autos.",
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
      "¿Esta seguro que desea borrar este auto?"
    );
    if (confirmDelete) {
      carService
        .remove(id)
        .then((response) => {
          console.log("Auto ha sido eliminado.", response.data);
          init();
        })
        .catch((error) => {
          console.log(
            "Se ha producido un error al intentar eliminar al auto",
            error
          );
        });
    }
  };

  const handleEdit = (id) => {
    console.log("Printing id", id);
    navigate(`/car/edit/${id}`);
  };

  const handleRepairs = (id) => {
    console.log("Printing id", id);
    navigate(`/repairs/${id}`);
  };

  return (
    <Box mt="4rem">
      <Typography sx={{ fontWeight: "bold"}} variant="h4" gutterBottom>
        Vehículos Registrados
      </Typography>
      <TableContainer sx={{ minWidth: 650, mb:"2rem" }} 
      component={Paper}>
        <br />
        <Link
          to="/car/add"
          style={{ textDecoration: "none", marginBottom: "1rem" }}
        >
          <Button
            variant="contained"
            color="primary"
            startIcon={<PersonAddIcon />}
            sx={{ backgroundColor: "#632654" }}
          >
            Añadir Auto
          </Button>
        </Link>
        <br /> <br />
        <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
          <TableHead>
            <TableRow>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>
                ID
              </TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>
                Patente
              </TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold" }}>
                Marca
              </TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold" }}>
                Modelo
              </TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold" }}>
                Tipo
              </TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold" }}>
                Año
              </TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold" }}>
                Motor
              </TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold" }}>
                Kms
              </TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold" }}>
                Asientos
              </TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>
                Operaciones
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {cars.map((car) => (
              <TableRow
                key={car.id}
                sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
              >
                <TableCell align="left">{car.id}</TableCell>
                <TableCell align="left">{car.patente}</TableCell>
                <TableCell align="right">{car.marca}</TableCell>
                <TableCell align="right">{car.modelo}</TableCell>
                <TableCell align="right">{car.type}</TableCell>
                <TableCell align="right">{car.year}</TableCell>
                <TableCell align="right">{car.motor}</TableCell>
                <TableCell align="right">{car.kms}</TableCell>
                <TableCell align="center">{car.seats}</TableCell>
                <TableCell>
                  <Button
                    variant="contained"
                    color="info"
                    sx={{ backgroundColor: "#632654" }}
                    size="small"
                    onClick={() => handleEdit(car.id)}
                    style={{ marginLeft: "0.5rem" }}
                    startIcon={<EditIcon />}
                  >
                    Editar
                  </Button>

                  <Button
                    variant="contained"
                    color="info"
                    sx={{ backgroundColor: "#632654" }}
                    size="small"
                    onClick={() => handleRepairs(car.id)}
                    style={{ marginLeft: "0.5rem" }}
                    startIcon={<Settings />}
                  >
                    Reparaciones
                  </Button>

                  <Button
                    variant="contained"
                    color="error"
                    size="small"
                    onClick={() => handleDelete(car.id)}
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

export default CarList;
