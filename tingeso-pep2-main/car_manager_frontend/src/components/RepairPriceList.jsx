import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import repairpriceService from '../services/repairprice.service';
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

const RepairPriceList = () => {
  const [rprices, setRprices] = useState([]);
  const navigate = useNavigate();

  const init = () => {
    repairpriceService
      .getAll()
      .then((response) => {
        console.log("Mostrando listado de todos los precios.", response.data);
        setRprices(response.data);
      })
      .catch((error) => {
        console.log(
          "Se ha producido un error al intentar mostrar listado de todos los precios.",
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
      "¿Esta seguro que desea borrar esta reparación?"
    );
    if (confirmDelete) {
      repairpriceService
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

  const handleEdit = (id) => {
    console.log("Printing id", id);
    navigate(`/rprice/edit/${id}`);
  };

  return (
    <Box mt="4rem">
      <Typography sx={{ fontWeight: "bold"}} variant="h4" gutterBottom>
        Precios de Reparaciones
      </Typography>
      <TableContainer sx={{ minWidth: 650, mb:"2rem" }} 
      component={Paper}>
        <br />
        <Link
          to="/rprice/add"
          style={{ textDecoration: "none", marginBottom: "1rem" }}
        >
          <Button
            variant="contained"
            color="primary"
            startIcon={<PersonAddIcon />}
            sx={{ backgroundColor: "#632654" }}
          >
            Añadir Precio de Reparación
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
                Tipo
              </TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold" }}>
                Precio Gasolina
              </TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold" }}>
                Precio Diésel
              </TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold" }}>
                Precio Híbrido
              </TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold" }}>
                Precio Eléctrico
              </TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>
                Operaciones
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {rprices.map((rprice) => (
              <TableRow
                key={rprice.id}
                sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
              >
                <TableCell align="left">{rprice.id}</TableCell>
                <TableCell align="left">{rprice.type}</TableCell>
                <TableCell align="right">${rprice.montoG}</TableCell>
                <TableCell align="right">${rprice.montoD}</TableCell>
                <TableCell align="right">${rprice.montoH}</TableCell>
                <TableCell align="right">${rprice.montoE}</TableCell>
                <TableCell>
                  <Button
                    variant="contained"
                    color="info"
                    sx={{ backgroundColor: "#632654" }}
                    size="small"
                    onClick={() => handleEdit(rprice.id)}
                    style={{ marginLeft: "0.5rem" }}
                    startIcon={<EditIcon />}
                  >
                    Editar
                  </Button>

                  <Button
                    variant="contained"
                    color="error"
                    size="small"
                    onClick={() => handleDelete(rprice.id)}
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

export default RepairPriceList;