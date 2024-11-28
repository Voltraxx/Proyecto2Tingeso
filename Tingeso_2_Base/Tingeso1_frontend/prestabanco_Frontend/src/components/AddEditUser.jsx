import { useState, useEffect } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import userService from "../services/user.service";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import FormControl from "@mui/material/FormControl";
import MenuItem from "@mui/material/MenuItem";
import SaveIcon from "@mui/icons-material/Save";

const AddEditUser = () => {
  const [name, setName] = useState("");
  const [age, setAge] = useState("");
  const [income, setIncome] = useState("");
  const [balance, setBalance] = useState("");
  const [debts, setDebts] = useState("");
  const [job, setJob] = useState("");
  const [account_age, setAccountAge] = useState("");
  const [withdrawals, setWithdrawals] = useState(Array(12).fill(""));
  const [deposits, setDeposits] = useState(Array(12).fill(""));
  const { id } = useParams();
  const [titleUserForm, setTitleUserForm] = useState("");
  const navigate = useNavigate();

  const handleWithdrawalChange = (index, value) => {
    const updatedWithdrawals = [...withdrawals];
    updatedWithdrawals[index] = parseFloat(value) || 0; // Asegura valores numéricos
    setWithdrawals(updatedWithdrawals);
  };

  const handleDepositChange = (index, value) => {
    const updatedDeposits = [...deposits];
    updatedDeposits[index] = parseFloat(value) || 0; // Asegura valores numéricos
    setDeposits(updatedDeposits);
  };

  const saveUser = (e) => {
    e.preventDefault();

    const user = { name, age, income, balance, debts, job, account_age, withdrawals, deposits, id };
    if (id) {
      //Actualizar Datos Usuario
      userService
        .update(user)
        .then((response) => {
          console.log("El usuario ha sido editado correctamente.", response.data);
          navigate("/UserList/");
        })
        .catch((error) => {
          console.log(
            "Ha ocurrido un error al intentar actualizar datos del usuario.",
            error
          );
        });
    } else {
      //Crear nuevo usuario
      userService
        .create(user)
        .then((response) => {
          console.log("El usuario ha sido añadido", response.data);
          navigate("/UserList/");
        })
        .catch((error) => {
          console.log(
            "Ha ocurrido un error al intentar crear nuevo usuario.",
            error
          );
        });
    }
  };

  useEffect(() => {
    if (id) {
      setTitleUserForm("Editar Usuario");
      userService
        .get(id)
        .then((user) => {
          setName(user.data.name);
          setAge(user.data.age);
          setIncome(user.data.income);
          setBalance(user.data.balance);
          setDebts(user.data.debts);
          setJob(user.data.job);
          setAccountAge(user.data.account_age);
          setWithdrawals(user.data.withdrawals || Array(12).fill(0));
          setDeposits(user.data.deposits || Array(12).fill(0));
        })
        .catch((error) => {
          console.log("Se ha producido un error.", error);
        });
    } else {
      setTitleUserForm("Nuevo Usuario");
    }
  }, []);

  return (
    <Box
      display="flex"
      flexDirection="column"
      alignItems="center"
      justifyContent="center"
      component="form"
    >
      <h3> {titleUserForm} </h3>
      <hr />
      <form>
        <FormControl fullWidth>
          <TextField
            id="name"
            label="Nombre"
            value={name}
            variant="standard"
            onChange={(e) => setName(e.target.value)}
            InputProps={{
              style: {
                color: 'white', // Color del texto dentro del cuadro de entrada
              },
            }}
            InputLabelProps={{
              style: { color: 'white' }, // Color de la etiqueta (label)
            }}
          />
        </FormControl>

        <FormControl fullWidth>
          <TextField
            id="age"
            label="Edad"
            type="number"
            value={age}
            variant="standard"
            onChange={(e) => setAge(e.target.value)}
            InputProps={{
              style: {
                color: 'white', // Color del texto dentro del cuadro de entrada
              },
            }}
            InputLabelProps={{
              style: { color: 'white' }, // Color de la etiqueta (label)
            }}
          />
        </FormControl>

        <FormControl fullWidth>
          <TextField
            id="income"
            label="Salario"
            type="number"
            value={income}
            variant="standard"
            onChange={(e) => setIncome(e.target.value)}
            InputProps={{
              style: {
                color: 'white', // Color del texto dentro del cuadro de entrada
              },
            }}
            InputLabelProps={{
              style: { color: 'white' }, // Color de la etiqueta (label)
            }}
          />
        </FormControl>

        <FormControl fullWidth>
          <TextField
            id="balance"
            label="Saldo"
            type="number"
            value={balance}
            variant="standard"
            onChange={(e) => setBalance(e.target.value)}
            InputProps={{
              style: {
                color: 'white', // Color del texto dentro del cuadro de entrada
              },
            }}
            InputLabelProps={{
              style: { color: 'white' }, // Color de la etiqueta (label)
            }}
          />
        </FormControl>

        <FormControl fullWidth>
          <TextField
            id="debts"
            label="Deudas"
            type="number"
            value={debts}
            variant="standard"
            onChange={(e) => setDebts(e.target.value)}
            InputProps={{
              style: {
                color: 'white', // Color del texto dentro del cuadro de entrada
              },
            }}
            InputLabelProps={{
              style: { color: 'white' }, // Color de la etiqueta (label)
            }}
          />
        </FormControl>

        <FormControl fullWidth>
          <TextField
            id="job"
            label="Tipo de Trabajador"
            value={job}
            variant="standard"
            onChange={(e) => setJob(e.target.value)}
            select // Agrega esta propiedad para que funcione como selector
            InputProps={{
              style: {
                color: 'white', // Color del texto dentro del cuadro de entrada
              },
            }}
            InputLabelProps={{
              style: { color: 'white' }, // Color de la etiqueta (label)
            }}
          >
            {/* Opciones para seleccionar */}
            <MenuItem value="Independiente">Independiente</MenuItem>
            <MenuItem value="Dependiente">Dependiente</MenuItem>
          </TextField>
        </FormControl>

        <FormControl fullWidth>
          <TextField
            id="account_age"
            label="Antigüedad de la Cuenta"
            type="number"
            value={account_age}
            variant="standard"
            onChange={(e) => setAccountAge(e.target.value)}
            InputProps={{
              style: {
                color: 'white', // Color del texto dentro del cuadro de entrada
              },
            }}
            InputLabelProps={{
              style: { color: 'white' }, // Color de la etiqueta (label)
            }}
          />
        </FormControl>

        <h4>Retiros Mensuales</h4>
        {withdrawals.map((withdrawal, index) => (
          <FormControl key={index} fullWidth>
            <TextField
              label={`Retiro ${index + 1}`}
              type="number"
              value={withdrawal}
              onChange={(e) => handleWithdrawalChange(index, e.target.value)}
              InputProps={{
                style: { color: 'white' },
              }}
              InputLabelProps={{
                style: { color: 'white' },
              }}
            />
          </FormControl>
        ))}

        <h4>Depositos Mensuales</h4>
        {deposits.map((deposit, index) => (
          <FormControl key={index} fullWidth>
            <TextField
              label={`Deposito ${index + 1}`}
              type="number"
              value={deposit}
              onChange={(e) => handleDepositChange(index, e.target.value)}
              InputProps={{
                style: { color: 'white' },
              }}
              InputLabelProps={{
                style: { color: 'white' },
              }}
            />
          </FormControl>
        ))}

        <FormControl>
          <br />
          <Button
            variant="contained"
            color="info"
            onClick={(e) => saveUser(e)}
            style={{ marginLeft: "0.5rem" }}
            startIcon={<SaveIcon />}
          >
            Guardar
          </Button>
        </FormControl>
      </form>
      <hr />
      <Link to="/UserList/">Volver a la lista</Link>
    </Box>
  );
};

export default AddEditUser;