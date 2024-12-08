import repairdetailService from '../services/repairdetail.service'
import { useState, useEffect } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell, { tableCellClasses } from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import { Box, Typography } from '@mui/material';
import Paper from "@mui/material/Paper";
import Button from "@mui/material/Button";
import PersonAddIcon from "@mui/icons-material/PersonAdd";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import { Add, Info, Settings } from "@mui/icons-material";

const RepairDetails = () => {
    const { id } = useParams();
    const [repairs, setRepairs] = useState([]);

    const navigate = useNavigate();

    const init = () => {
        repairdetailService
            .getByIdRepair(id)
            .then((response) => {
                console.log("Mostrando reparacion.", response.data);
                setRepairs(response.data);
            })
            .catch((error) => {
                console.log(
                    "Se ha producido un error al intentar mostrar la reparacion.",
                    error
                );
            });
    };

    useEffect(() => {
        init();
    }, []);

    return (
        <Box align="center" mt="5rem">
            <Typography sx={{ fontWeight: "bold" }} mb="3rem" variant="h4" gutterBottom>
                Detalles de Reparación
            </Typography>

            <TableContainer sx={{ minWidth: 650, mb: "2rem" }}
                component={Paper}>
            
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
                                Monto
                            </TableCell>
                            <TableCell align="right" sx={{ fontWeight: "bold" }}>
                                Patente
                            </TableCell>
                            <TableCell align="right" sx={{ fontWeight: "bold" }}>
                                Fecha Ingreso
                            </TableCell>
                            <TableCell align="right" sx={{ fontWeight: "bold" }}>
                                Hora Ingreso
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
                                <TableCell align="left">{repair.type}</TableCell>
                                <TableCell align="right">${repair.monto}</TableCell>
                                <TableCell align="right">{repair.patente}</TableCell>
                                <TableCell align="right">{repair.fechaIngreso}</TableCell>
                                <TableCell align="right">{repair.horaIngreso}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </Box>

    );
};

export default RepairDetails;