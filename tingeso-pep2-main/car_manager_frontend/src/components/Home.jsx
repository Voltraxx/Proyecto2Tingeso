import { Box, Typography } from "@mui/material";

const Home = () => {
  return (
    <Box sx={{mt:"4rem"}}>
      <h1>AutoFix: Sistema de Gestión de Reparaciones</h1>
      <p>
        AutoFix es una aplicación web para gestionar reparaciones de autos.
        Esta aplicación ha sido desarrollada usando tecnologías como{" "}
        <a href="https://spring.io/projects/spring-boot">Spring Boot</a> (para
        el backend), <a href="https://reactjs.org/">React</a> (para el Frontend)
        y <a href="https://www.postgresql.org/">PostgreSQL</a> (para la
        base de datos).
      </p>
    </Box>

    
  );
};

export default Home;
