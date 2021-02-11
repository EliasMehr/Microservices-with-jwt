import React, { useContext } from "react";
import { makeStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";
import IconButton from "@material-ui/core/IconButton";
import MenuIcon from "@material-ui/icons/Menu";
import { Link } from "react-router-dom";
import { UserContext } from "../../context/UserContext";
import { useHistory } from "react-router-dom";
import authService from "../../services/authService";

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
  },
  menuButton: {
    marginRight: theme.spacing(2),
  },
  title: {
    flexGrow: 1,
  },
}));

export default function Navbar() {
  const classes = useStyles();
  let history = useHistory();
  const [user, userDispatch] = useContext(UserContext);

  const handleLogout = () => {
    userDispatch({ type: "LOGOUT" });
    authService.logout();
    history.push("/");
  };

  return (
    <div className={classes.root}>
      <AppBar position="fixed">
        <Toolbar>
          <IconButton
            edge="start"
            className={classes.menuButton}
            color="inherit"
            aria-label="menu"
          >
            <MenuIcon />
          </IconButton>
          <Typography variant="h6" className={classes.title}>
            <Button color="inherit" component={Link} to={"/"}>
              CampaignBoys
            </Button>
          </Typography>
          <Button color="inherit" component={Link} to={"/"}>
            Home
          </Button>
          {user.role === "COMPANY" ? (
            <Button color="inherit" component={Link} to={"/company"}>
              Company Dashboard
            </Button>
          ) : null}
          {user.isLoggedIn ? (
            <Button color="inherit" onClick={() => handleLogout()}>
              Logout
            </Button>
          ) : (
            <>
              <Button color="inherit" component={Link} to={"/login"}>
                Login
              </Button>
              <Button color="inherit" component={Link} to={"/Signup"}>
                Sign up
              </Button>
            </>
          )}
        </Toolbar>
      </AppBar>
    </div>
  );
}
