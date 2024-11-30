import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import userService from "../services/user.service";
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

const UserList = () => {
  const [users, setUsers] = useState([]);

  const navigate = useNavigate();

  const init = () => {
    userService
      .getAll()
      .then((response) => {
        console.log("Mostrando listado de todos los usuarios.", response.data);
        setUsers(response.data);
      })
      .catch((error) => {
        console.log(
          "Se ha producido un error al intentar mostrar listado de todos los usuarios.",
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
      "¿Esta seguro que desea borrar este usuario?"
    );
    if (confirmDelete) {
      userService
        .remove(id)
        .then((response) => {
          console.log("usuario ha sido eliminado.", response.data);
          init();
        })
        .catch((error) => {
          console.log(
            "Se ha producido un error al intentar eliminar al usuario",
            error
          );
        });
    }
  };

  const handleEdit = (id) => {
    console.log("Printing id", id);
    navigate(`/user/edit/${id}`);
  };

  return (
    <TableContainer component={Paper}>
      <br />
      <Link
        to="/user/add"
        style={{ textDecoration: "none", marginBottom: "1rem" }}
      >
        <Button
          variant="contained"
          color="primary"
          startIcon={<PersonAddIcon />}
        >
          Añadir usuario
        </Button>
      </Link>
      <br /> <br />
      <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
        <TableHead>
          <TableRow>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>
              Nombre
            </TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>
              Edad
            </TableCell>
            <TableCell align="right" sx={{ fontWeight: "bold" }}>
              Salario
            </TableCell>
            <TableCell align="right" sx={{ fontWeight: "bold" }}>
              Saldo
            </TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>
              Deudas
            </TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>
              Tipo de Trabajador
            </TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>
              Acciones
            </TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {users.map((user) => (
            <TableRow
              key={user.id}
              sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
            >
              <TableCell align="left">{user.name}</TableCell>
              <TableCell align="left">{user.age}</TableCell>
              <TableCell align="right">{user.income}</TableCell>
              <TableCell align="right">{user.balance}</TableCell>
              <TableCell align="right">{user.debts}</TableCell>
              <TableCell align="right">{user.job}</TableCell>
              <TableCell>
                <Button
                  variant="contained"
                  color="info"
                  size="small"
                  onClick={() => handleEdit(user.id)}
                  style={{ marginLeft: "0.5rem" }}
                  startIcon={<EditIcon />}
                >
                  Editar
                </Button>

                <Button
                  variant="contained"
                  color="error"
                  size="small"
                  onClick={() => handleDelete(user.id)}
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
  );
};

export default UserList;