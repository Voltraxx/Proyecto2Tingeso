import { useState, useEffect } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import carService from "../services/car.service";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import FormControl from "@mui/material/FormControl";
import MenuItem from "@mui/material/MenuItem";
import SaveIcon from "@mui/icons-material/Save";

const AddEditCar = () => {
  const { id } = useParams();
  const [patente, setPatente] = useState("");
  const [marca, setMarca] = useState("");
  const [modelo, setModelo] = useState("");
  const [type, setType] = useState("");
  const [motor, setMotor] = useState("");
  const [year, setYear] = useState(0);
  const [seats, setSeats] = useState(0);
  const [kms, setKms] = useState(0);
  const [titleCarForm, setTitleCarForm] = useState("");
  const navigate = useNavigate();

  const saveCar = (e) => {
    e.preventDefault();

    // Validación de campos vacíos
    if (!patente || !marca || !modelo || !type || !motor || !year || !seats || !kms) {
      alert("Por favor complete todos los campos.");
      return;
    }

    const car = { id, patente, marca, modelo, type, motor, year, seats, kms };
    if (id) {
      //Actualizar Datos de Auto
      carService
        .update(car)
        .then((response) => {
          console.log("Auto ha sido actualizado.", response.data);
          navigate("/car/list");
        })
        .catch((error) => {
          console.log(
            "Ha ocurrido un error al intentar actualizar datos del auto.",
            error
          );
        });
    } else {
      //Crear nuevo auto
      carService
        .create(car)
        .then((response) => {
          console.log("Auto ha sido añadido.", response.data);
          navigate("/car/list");
        })
        .catch((error) => {
          console.log(
            "Ha ocurrido un error al intentar crear nuevo auto.",
            error
          );
        });
    }
  };

  useEffect(() => {
    if (id) {
      setTitleCarForm("Editar Auto");
      carService
        .get(id)
        .then((car) => {
          setPatente(car.data.patente);
          setMarca(car.data.marca);
          setModelo(car.data.modelo);
          setType(car.data.type);
          setMotor(car.data.motor);
          setYear(car.data.year);
          setSeats(car.data.seats);
          setKms(car.data.kms);
        })
        .catch((error) => {
          console.log("Se ha producido un error.", error);
        });
    } else {
      setTitleCarForm("Registrar Auto Nuevo");
    }
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
      <h3> {titleCarForm} </h3>

      <form>
        <FormControl fullWidth>
          <TextField
            id="patente"
            label="Patente"
            value={patente}
            variant="outlined"
            onChange={(e) => setPatente(e.target.value)}
            style={{ margin: "5px" }}
            helperText="Ej. AA1122"
          />
        </FormControl>

        <FormControl fullWidth>
          <TextField
            id="marca"
            label="Marca"
            value={marca}
            variant="outlined"
            onChange={(e) => setMarca(e.target.value)}
            style={{ margin: "5px" }}
          />
        </FormControl>

        <FormControl fullWidth>
          <TextField
            id="modelo"
            label="Modelo"
            value={modelo}
            variant="outlined"
            onChange={(e) => setModelo(e.target.value)}
            style={{ margin: "5px" }}
          />
        </FormControl>

        <FormControl fullWidth>
          <TextField
            id="type"
            label="Tipo"
            value={type}
            select
            variant="outlined"
            onChange={(e) => setType(e.target.value)}
            defaultValue="Sedan"
            style={{ margin: "5px" }}
          >
            <MenuItem value={"Sedan"}>Sedán</MenuItem>
            <MenuItem value={"Hatchback"}>Hatchback</MenuItem>
            <MenuItem value={"SUV"}>SUV</MenuItem>
            <MenuItem value={"Pickup"}>Pickup</MenuItem>
            <MenuItem value={"Furgoneta"}>Furgoneta</MenuItem>
          </TextField>
        </FormControl>

        <FormControl fullWidth>
          <TextField
            id="motor"
            label="Motor"
            value={motor}
            select
            variant="outlined"
            defaultValue="Gasolina"
            style={{ margin: "5px" }}
            onChange={(e) => setMotor(e.target.value)}
          >
            <MenuItem value={"Gasolina"}>Gasolina</MenuItem>
            <MenuItem value={"Diésel"}>Diésel</MenuItem>
            <MenuItem value={"Híbrido"}>Híbrido</MenuItem>
            <MenuItem value={"Eléctrico"}>Eléctrico</MenuItem>
          </TextField>
        </FormControl>

        <FormControl fullWidth>
          <TextField
            id="year"
            label="Año"
            type="number"
            value={year}
            variant="outlined"
            onChange={(e) => setYear(e.target.value)}
            style={{ margin: "5px" }}

          />
        </FormControl>

        <FormControl fullWidth>
          <TextField
            id="seats"
            label="Número de asientos"
            type="number"
            value={seats}
            variant="outlined"
            onChange={(e) => setSeats(e.target.value)}
            style={{ margin: "5px" }}
          />
        </FormControl>

        <FormControl fullWidth>
          <TextField
            id="kms"
            label="Kilometraje"
            type="number"
            value={kms}
            variant="outlined"
            onChange={(e) => setKms(e.target.value)}
            style={{ margin: "5px" }}
          />
        </FormControl>

        <FormControl>
          <br />
          <Button
            variant="contained"
            color="info"
            onClick={(e) => saveCar(e)}
            style={{ marginLeft: "0.5rem" }}
            startIcon={<SaveIcon />}
          >
            Guardar
          </Button>
        </FormControl>
      </form>
      <br />
      <Link to="/car/list">Back to List</Link>
    </Box>
  );
};

export default AddEditCar;
