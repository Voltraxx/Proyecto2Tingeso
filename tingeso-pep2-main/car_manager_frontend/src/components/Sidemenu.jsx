import * as React from "react";
import Box from "@mui/material/Box";
import Drawer from "@mui/material/Drawer";
import List from "@mui/material/List";
import Divider from "@mui/material/Divider";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import HomeIcon from "@mui/icons-material/Home";
import { useNavigate } from "react-router-dom";
import { AccessTime, Build, CarRepair, DirectionsCar, MonetizationOn, Money } from "@mui/icons-material";

export default function Sidemenu({ open, toggleDrawer }) {
  const navigate = useNavigate();

  const listOptions = () => (
    <Box
      role="presentation"
      onClick={toggleDrawer(false)}
      sx={{ width: 250 }}
    >
      <List>
        <ListItemButton onClick={() => navigate("/home")}>
          <ListItemIcon>
            <HomeIcon />
          </ListItemIcon>
          <ListItemText primary="Home" />
        </ListItemButton>


        <ListItemButton onClick={() => navigate("/car/list")}>
          <ListItemIcon>
            <DirectionsCar />
          </ListItemIcon>
          <ListItemText primary="Autos Registrados" />
        </ListItemButton>

        <ListItemButton onClick={() => navigate("/rprice/list")}>
          <ListItemIcon>
            <Build />
          </ListItemIcon>
          <ListItemText primary="Repairs Precios" />
        </ListItemButton>

        <ListItemButton onClick={() => navigate("/allrepairs")}>
          <ListItemIcon>
            <CarRepair />
          </ListItemIcon>
          <ListItemText primary="All Repairs" />
        </ListItemButton>

        <ListItemButton onClick={() => navigate("/bono/list")}>
          <ListItemIcon>
            <Money />
          </ListItemIcon>
          <ListItemText primary="Bonos" />
        </ListItemButton>
        
        <Divider />

        <ListItemButton onClick={() => navigate("/reporte1")}>
          <ListItemIcon>
            <MonetizationOn />
          </ListItemIcon>
          <ListItemText primary="Reporte 1" />
        </ListItemButton>

        <ListItemButton onClick={() => navigate("/reporte2")}>
          <ListItemIcon>
            <Build />
          </ListItemIcon>
          <ListItemText primary="Reporte 2" />
        </ListItemButton>
      </List>
    </Box>
  );

  return (
    <div>
      <Drawer anchor={"left"} open={open} onClose={toggleDrawer(false)}>
        {listOptions()}
      </Drawer>
    </div>
  );
}
