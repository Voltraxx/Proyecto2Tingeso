import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { Link, useNavigate } from "react-router-dom";
import userService from "../services/user.service";
import creditRequestService from "../services/credit.request.service";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";

const EvaluateCreditRequest = () => {
  const { id } = useParams();
  const [evaluationResults, setEvaluationResults] = useState({
    R1: null,
    R2: null,
    R3: null,
    R4: null,
    R5: null,
    R6: null,
    R7: null,
    R71: null,
    R72: null,
    R73: null,
    R74: null,
    R75: null,
  });
  const [creditRequestData, setCreditRequestData] = useState(null);
  const navigate = useNavigate();
  const [usersList, setUsersList] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [creditRequestResponse, usersResponse] = await Promise.all([
          creditRequestService.get(id),
          userService.getAll(),
        ]);
        setCreditRequestData(creditRequestResponse.data);
        setUsersList(usersResponse.data);
      } catch (error) {
        console.error("Error al cargar los datos:", error);
      }
    };
  
    fetchData();
  }, [id]);

  const evaluateRule = async (rule) => {
    try {
      const response = await creditRequestService.evaluateRule(id, rule);
      setEvaluationResults((prevResults) => ({
        ...prevResults,
        [rule]: response.data,
      }));
    } catch (error) {
      console.error("Error evaluando la regla", rule, error);
    }
  };

  const handleDownload = async (docNumber) => {
    try {
      const response = await creditRequestService.downloadDocument(id, docNumber);
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", `document${docNumber}.pdf`);
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    } catch (error) {
      console.error("Error al descargar el documento:", error);
    }
  };

   const handleAcceptReject = (rule, accepted) => {
     setEvaluationResults((prevResults) => ({
       ...prevResults,
       [rule]: accepted,
     }));
   };

  const handleApprove = async () => {
      try {
          // await creditRequestService.updateStatus(id, "approve");
          navigate("/creditRequestList");
      } catch (error) {
          console.error("Error al aprobar la solicitud", error);
      }
  };

  const handleReject = async () => {
      try {
          // await creditRequestService.updateStatus(id, "reject");
          navigate("/creditRequestList");
      } catch (error) {
          console.error("Error al rechazar la solicitud", error);
      }
  };

  // Función para cargar la lista de usuarios
  const fetchUsers = async () => {
    try {
      const response = await userService.getAll();
      console.log("Usuarios cargados:", response.data);
      setUsersList(response.data); // Actualiza el estado con la lista de usuarios
    } catch (error) {
      console.error("Error al cargar la lista de usuarios:", error);
    }
  };

  // useEffect para cargar usuarios al montar el componente
  useEffect(() => {
    fetchUsers();
  }, []);

   // Función para obtener un usuario por ID
   const getUserById = (userId) => {
    const user = usersList.find((user) => user.id === userId);
    return user || null; // Retorna el objeto usuario completo o null si no se encuentra
  };

  if (!creditRequestData) return <p>Cargando datos de la solicitud...</p>;

  const CRuserId = creditRequestData.userId;
  const CRuser = getUserById(CRuserId);
  return (
    <Box display="flex" flexDirection="column" alignItems="center">
      <h2>Evaluar Solicitud de Crédito</h2>

      {/* R1: Relación Cuota/Ingreso */}
      <Box
        bgcolor={evaluationResults.R1 === true ? "green" : evaluationResults.R1 === false ? "red" : "gray"}
        p={2}
        m={1}
        width="90%"
        display="flex"
        flexDirection="column"
        alignItems="center"
      >
        <h4>Relación Cuota/Ingreso (R1)</h4>
        <p>Cuota Mensual: {creditRequestData.monthlyQuota}</p>
        <p>Ingreso Mensual: {CRuser.income}</p>
        <p>Relación Cuota/Ingreso: {(creditRequestData.monthlyQuota / CRuser.income).toFixed(2)}</p>
        <Button variant="contained" onClick={() => evaluateRule("R1")}>
          Evaluar
        </Button>
      </Box>

      {/* R2: Historial Crediticio */}
      <Box
        bgcolor={evaluationResults.R2 === true ? "green" : evaluationResults.R2 === false ? "red" : "gray"}
        p={2}
        m={1}
        width="90%"
        display="flex"
        flexDirection="column"
        alignItems="center"
      >
        <h4>Historial crediticio del cliente (R2)</h4>
        <p>Historial: {CRuser.creditHistory}</p>

        <Button
          variant="contained"
          style={{ backgroundColor: "#9b59b6", color: "white", margin: "10px 0" }}
          onClick={() => handleDownload(1)} // Aquí se llama a handleDownload pasando el número del documento
        >
          Descargar historial crediticio
        </Button>

        <Box display="flex" justifyContent="center" mt={2}>
          <Button
            variant="contained"
            color="success"
            onClick={() => handleAcceptReject("R2", true)}
            style={{ marginRight: "10px" }}
          >
            Aceptar
          </Button>
          <Button
            variant="contained"
            color="error"
            onClick={() => handleAcceptReject("R2", false)}
          >
            Rechazar
          </Button>
        </Box>
      </Box>

      {/* R3: Antigüedad laboral y estabilidad */}
      <Box
        bgcolor={evaluationResults.R3 === true ? "green" : evaluationResults.R3 === false ? "red" : "gray"}
        p={2}
        m={1}
        width="90%"
        display="flex"
        flexDirection="column"
        alignItems="center"
      >
        <h4>Antigüedad laboral y estabilidad (R3)</h4>
        <p>Tipo de Trabajador: {CRuser.job}</p>
        
        <Button
          variant="contained"
          style={{ backgroundColor: "#9b59b6", color: "white", margin: "10px 0" }}
          onClick={() => handleDownload(1)} // Aquí se llama a handleDownload pasando el número del documento
        >
          Descargar historial de ingresos
        </Button>

        <Box display="flex" justifyContent="center" mt={2}>
          <Button
            variant="contained"
            color="success"
            onClick={() => handleAcceptReject("R3", true)}
            style={{ marginRight: "10px" }}
          >
            Aceptar
          </Button>
          <Button
            variant="contained"
            color="error"
            onClick={() => handleAcceptReject("R3", false)}
          >
            Rechazar
          </Button>
        </Box>
      </Box>

      {/* R4: Relación Deuda/Ingreso */}
      <Box
        bgcolor={evaluationResults.R4 === true ? "green" : evaluationResults.R4 === false ? "red" : "gray"}
        p={2}
        m={1}
        width="90%"
        display="flex"
        flexDirection="column"
        alignItems="center"
      >
        <h4>Relación Deuda/Ingreso (R4)</h4>
        <p>Deudas (+cuota): {CRuser.debts + creditRequestData.monthlyQuota}</p>
        <p>Salario Usuario: {CRuser.income}</p>
        <p>Relación Deuda/Ingreso: {((CRuser.debts + creditRequestData.monthlyQuota)/ CRuser.income).toFixed(2)}</p>
        <Button variant="contained" onClick={() => evaluateRule("R4")}>
          Evaluar
        </Button>
      </Box>

      {/* R4: Relación Deuda/Ingreso */}
      <Box
        bgcolor={evaluationResults.R5 === true ? "green" : evaluationResults.R5 === false ? "red" : "gray"}
        p={2}
        m={1}
        width="90%"
        display="flex"
        flexDirection="column"
        alignItems="center"
      >
        <h4>Monto Máximo de Financiamiento (R5)</h4>
        <p>Valor de la propiedad: {(creditRequestData.propertyValue).toFixed(2)}</p>
        <p>Valor máximo de Financiamiento: {creditRequestData.loanValue}</p>
        <Button variant="contained" onClick={() => evaluateRule("R5")}>
          Evaluar
        </Button>
      </Box>

      {/* R6: Edad del Solicitante */}
      <Box
        bgcolor={evaluationResults.R6 === true ? "green" : evaluationResults.R6 === false ? "red" : "gray"}
        p={2}
        m={1}
        width="90%"
        display="flex"
        flexDirection="column"
        alignItems="center"
      >
        <h4>Edad del Solicitante (R6)</h4>
        <p>Edad actual del solicitante: {CRuser.age}</p>
        <p>Edad del solicitante al final del préstamo: {CRuser.age + creditRequestData.term}</p>
        <Button variant="contained" onClick={() => evaluateRule("R6")}>
          Evaluar
        </Button>
      </Box>

      {/* R7: Capacidad de Ahorro */}
      <Box
        bgcolor={evaluationResults.R7 === true ? "green" : evaluationResults.R7 === false ? "red" : "gray"}
        p={2}
        m={1}
        width="90%"
        display="flex"
        flexDirection="column"
        alignItems="center"
      >
        <h4>Capacidad de Ahorro (R7)</h4>

        {/* Subcuadro R71: Saldo Mínimo Requerido */}
        <Box
          bgcolor={evaluationResults.R71 === true ? "green" : evaluationResults.R71 === false ? "red" : "gray"}
          p={2}
          m={1}
          width="90%"
          display="flex"
          flexDirection="column"
          alignItems="start"
          border="1px solid #ccc"
          borderRadius="5px"
        >
          <h5>Saldo Mínimo Requerido (R71)</h5>
          <p>El cliente debe tener un saldo mínimo de al menos el 10% del monto del préstamo solicitado.</p>
          <p>Saldo de la cuenta: {CRuser.balance}</p>
          <p>Valor del préstamo: {creditRequestData.loanValue}</p>
          <p>Porcentaje en la cuenta de ahorro: {(CRuser.balance/creditRequestData.loanValue).toFixed(2)}%</p>
          
          {/* Botón Evaluar */}
          <Button variant="contained" onClick={() => evaluateRule("R71")} style={{ marginTop: "10px" }}>
            Evaluar
          </Button>
        </Box>

        {/* Subcuadro R72: Historial de Ahorro Consistente */}
        <Box
          bgcolor={evaluationResults.R72 === true ? "green" : evaluationResults.R72 === false ? "red" : "gray"}
          p={2}
          m={1}
          width="90%"
          display="flex"
          flexDirection="column"
          alignItems="start"
          border="1px solid #ccc"
          borderRadius="5px"
        >
          <h5>Historial de Ahorro Consistente (R72)</h5>
          <p>El cliente debe haber mantenido un saldo positivo en su cuenta de ahorros por lo menos durante los últimos 12 meses, sin realizar retiros significativos (+50% del saldo).</p>
          <p>Saldo de la cuenta: {CRuser.balance}</p>
          
          <p>Últimos 12 retiros (valores en bruto):</p>
          <ul>
            {CRuser.withdrawals.slice(-12).map((withdrawal, index) => (
              <li key={index}>{withdrawal}</li>
            ))}
          </ul>

          {/* Lista de porcentajes de los últimos 12 retiros */}
          <p>Porcentajes de los últimos 12 retiros:</p>
          <ul>
            {CRuser.withdrawals.slice(-12).map((withdrawal, index) => (
              <li key={index}>
                {((withdrawal / CRuser.balance) * 100).toFixed(2)}%
              </li>
            ))}
          </ul>
          
          {/* Botón Evaluar */}
          <Button variant="contained" onClick={() => evaluateRule("R72")} style={{ marginTop: "10px" }}>
            Evaluar
          </Button>
        </Box>

        {/* Subcuadro R73: Depósitos Periódicos */}
        <Box
          bgcolor={evaluationResults.R73 === true ? "green" : evaluationResults.R73 === false ? "red" : "gray"}
          p={2}
          m={1}
          width="90%"
          display="flex"
          flexDirection="column"
          alignItems="start"
          border="1px solid #ccc"
          borderRadius="5px"
        >
          <h5>Depósitos Periódicos (R73)</h5>
          <p>El cliente debe realizar depósitos regulares en su cuenta de ahorros o inversión. Los depósitos deben sumar al menos el 5% de sus ingresos mensuales, y no debe haber 3 o más meses consecutivos sin depósitos.</p>
          <p>Ingreso mensual: {CRuser.income}</p>
          <p>Monto mínimo requerido (5% del ingreso mensual): {(CRuser.income * 0.05).toFixed(2)}</p>
          
          {/* Lista de valores brutos de los últimos 12 depósitos */}
          <p>Últimos 12 depósitos (valores en bruto):</p>
          <ul>
            {CRuser.deposits.slice(-12).map((deposit, index) => (
              <li key={index}>{deposit}</li>
            ))}
          </ul>

          <p>Suma total de los depósitos: {CRuser.deposits.slice(-12).reduce((sum, deposit) => sum + deposit, 0).toFixed(2)}</p>

          {/* Botón Evaluar */}
          <Button variant="contained" onClick={() => evaluateRule("R73")} style={{ marginTop: "10px" }}>
            Evaluar
          </Button>
        </Box>

        {/* Subcuadro R74: Relación Saldo/Años de Antigüedad */}
        <Box
          bgcolor={evaluationResults.R74 === true ? "green" : evaluationResults.R74 === false ? "red" : "gray"}
          p={2}
          m={1}
          width="90%"
          display="flex"
          flexDirection="column"
          alignItems="start"
          border="1px solid #ccc"
          borderRadius="5px"
        >
          <h5>Relación Saldo/Años de Antigüedad (R74)</h5>
          <p>Si el cliente tiene menos de 2 años en la cuenta de ahorros, debe tener un saldo acumulado de al menos el 20% del préstamo solicitado. Si tiene 2 años o más, solo basta con el 10%</p>
          <p>Saldo acumulado: {CRuser.balance}</p>
          <p>Valor del préstamo: {creditRequestData.loanValue}</p>
          <p>Antigüedad de la Cuenta: {CRuser.account_age}</p>
          <p>Porcentaje de la Cuenta de Ahorro: {((CRuser.balance/creditRequestData.loanValue)*100).toFixed(2)}%</p>
          {/* Botón Evaluar */}
          <Button variant="contained" onClick={() => evaluateRule("R74")} style={{ marginTop: "10px" }}>
            Evaluar
          </Button>
        </Box>

        {/* Subcuadro R75: Retiros Recientes */}
        <Box
          bgcolor={evaluationResults.R75 === true ? "green" : evaluationResults.R75 === false ? "red" : "gray"}
          p={2}
          m={1}
          width="90%"
          display="flex"
          flexDirection="column"
          alignItems="start"
          border="1px solid #ccc"
          borderRadius="5px"
        >
          <h5>Retiros Recientes (R75)</h5>
          <p>Si el cliente ha realizado un retiro superior al 30% del saldo de su cuenta en los últimos 6 meses, marcar este punto como negativo, ya que indica una posible falta de estabilidad financiera.</p>
          <p>Saldo de la cuenta: {CRuser.balance}</p>

          {/* Lista de valores brutos de los últimos 6 retiros */}
          <p>Retiros de los últimos 6 meses:</p>
          <ul>
            {CRuser.withdrawals.slice(-6).map((withdrawal, index) => (
              <li key={index}>{withdrawal}</li>
            ))}
          </ul>

          {/* Lista de porcentajes de los últimos 6 retiros */}
          <p>Porcentajes de los últimos 6 retiros:</p>
          <ul>
            {CRuser.withdrawals.slice(-6).map((withdrawal, index) => (
              <li key={index}>
                {((withdrawal / CRuser.balance) * 100).toFixed(2)}%
              </li>
            ))}
          </ul>
          {/* Botón Evaluar */}
          <Button variant="contained" onClick={() => evaluateRule("R75")} style={{ marginTop: "10px" }}>
            Evaluar
          </Button>
        </Box>

        <Box display="flex" justifyContent="center" mt={2}>
          <Button
            variant="contained"
            color="success"
            onClick={() => handleAcceptReject("R7", true)}
            style={{ marginRight: "10px" }}
          >
            Aceptar
          </Button>
          <Button
            variant="contained"
            color="error"
            onClick={() => handleAcceptReject("R7", false)}
          >
            Rechazar
          </Button>
        </Box>
      </Box>
      <Box mt={4} display="flex" justifyContent="center">
        {creditRequestData?.status === "En Evaluación" && (
          <>
            <Button variant="contained" color="success" onClick={handleApprove}>Aceptar</Button>
            <Button variant="contained" color="error" onClick={handleReject} style={{ marginLeft: "1rem" }}>Rechazar</Button>
          </>
        )}
      </Box>
    </Box>
  );
};

export default EvaluateCreditRequest;
