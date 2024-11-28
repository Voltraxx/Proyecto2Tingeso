import { React, useState } from 'react';
import { TextField, Button, Box, Typography } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import creditSimulationService from "../services/credit.simulation.service";

const CreditCalculator = () => {
  const [loan, setLoan] = useState("");
  const [payment_quantity, setPaymentQuantity] = useState("");
  const [interest, setInterest] = useState("");
  const [resultado, setResultado] = useState(null); // Estado para almacenar el valor del crédito calculado
  const navigate = useNavigate();

  const calculateCreditSimulation = (e) => {
    e.preventDefault(); // Asegura que el formulario no se envíe por defecto

    // Verifica que los campos no estén vacíos antes de hacer la solicitud
    if (!loan || !interest || !payment_quantity) {
      console.error("Todos los campos deben estar completos.");
      return;
    }

    creditSimulationService
      .calculateCredit(loan, interest/12/100, payment_quantity)
      .then((response) => {
        console.log("Se ha calculado el crédito.", response.data);
        setResultado(response.data); // Almacena el resultado en el estado
      })
      .catch((error) => {
        console.error("Error al calcular el crédito", error);
      });
  };

  return (
    <Box sx={{ padding: '20px' }}>
      <h1>Simulador de Crédito</h1>
      <p>Ingrese los valores respectivos para calcular la cuota mensual a pagar</p>

      {/* El formulario está correctamente configurado para capturar el evento de envío */}
      <Box component="form" sx={{ display: 'flex', flexDirection: 'column', gap: '20px', maxWidth: '400px' }} onSubmit={calculateCreditSimulation}>
        <TextField
          label="Monto Préstamo"
          variant="outlined"
          fullWidth
          value={loan}
          onChange={(e) => setLoan(e.target.value)} // Actualiza el estado
          sx={{ backgroundColor: 'white' }} // Fondo blanco
        />
        <TextField
          label="Tasa Interés Anual"
          variant="outlined"
          fullWidth
          value={interest}
          onChange={(e) => setInterest(e.target.value)} // Actualiza el estado
          sx={{ backgroundColor: 'white' }} // Fondo blanco
        />
        <TextField
          label="Número Pagos Totales"
          variant="outlined"
          fullWidth
          value={payment_quantity}
          onChange={(e) => setPaymentQuantity(e.target.value)} // Actualiza el estado
          sx={{ backgroundColor: 'white' }} // Fondo blanco
        />

        {/* El botón debe estar dentro del formulario y tener type="submit" */}
        <Button variant="contained" color="primary" type="submit" sx={{ backgroundColor: 'green' }}>
          Calcular
        </Button>
      </Box>

      {/* Mostrar el resultado solo si está disponible */}
      {resultado !== null && (
        <Typography variant="h6" sx={{ marginTop: '20px' }}>
          El valor del crédito es: {resultado.toFixed(2)}
        </Typography>
      )}
    </Box>
  );
};

export default CreditCalculator;