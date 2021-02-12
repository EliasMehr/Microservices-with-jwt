import React, { useContext, useState, useEffect } from "react";
import { makeStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";
import IconButton from "@material-ui/core/IconButton";
import MenuIcon from "@material-ui/icons/Menu";
import { Link as LinkRoute } from "react-router-dom";
import { UserContext } from "../../context/UserContext";
import { useHistory } from "react-router-dom";
import authService from "../../services/authService";
import Box from "@material-ui/core/Box";
import Container from "@material-ui/core/Container";
import Link from "@material-ui/core/Link";

// const useStyles = makeStyles((theme) => ({
//   root: {
//     flexGrow: 1,
//   },
//   menuButton: {
//     marginRight: theme.spacing(2),
//   },
//   title: {
//     flexGrow: 1,
//   },
// }));

const useStyles = makeStyles((theme) => ({
  toolbar: {
    minHeight: 34,
    display: "flex",
    flexDirection: "column",
    justifyContent: "space-between",
    alignItems: "center",
    position: "static",
    padding: theme.spacing(3),
    [theme.breakpoints.up("md")]: {
      // position: "fixed",
      flexDirection: "row",
      justifyContent: "space-between",
      alignItems: "flex-end",
    },
  },
  toolbarTitle: {
    letterSpacing: 1.25,
    fontWeight: "bold",
  },
  menuButtons: {
    display: "flex",
    flexDirection: "column",
    [theme.breakpoints.up("md")]: {
      flexDirection: "row",
    },
  },
  item: {
    padding: theme.spacing(1),
    [theme.breakpoints.up("md")]: {
      paddingLeft: theme.spacing(3),
    },
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

  // return (
  //   <div className={classes.root}>
  //     <AppBar position="fixed">
  //       <Toolbar className={classes.toolbar}>
  //         <Typography
  //           component="h2"
  //           variant="h5"
  //           color="inherit"
  //           align="left"
  //           noWrap
  //           className={classes.toolbarTitle}
  //         >
  //           <Button color="inherit" component={Link} to={"/"}>
  //             CampaignBoys
  //           </Button>
  //         </Typography>
  //         <Button color="inherit" component={Link} to={"/"}>
  //           Home
  //         </Button>
  //         {user.role === "COMPANY" ? (
  //           <Button color="inherit" component={Link} to={"/company"}>
  //             Company Dashboard
  //           </Button>
  //         ) : null}
  //         {user.isLoggedIn ? (
  //           <Button color="inherit" onClick={() => handleLogout()}>
  //             Logout
  //           </Button>
  //         ) : (
  //           <>
  //             <Button color="inherit" component={Link} to={"/login"}>
  //               Login
  //             </Button>
  //             <Button color="inherit" component={Link} to={"/Signup"}>
  //               Sign up
  //             </Button>
  //           </>
  //         )}
  //       </Toolbar>
  //     </AppBar>
  //   </div>
  // );

  return (
    // <div className={classes.root}>
    <Container>
      <Toolbar className={classes.toolbar}>
        <Typography
          component="h2"
          variant="h5"
          color="inherit"
          align="left"
          noWrap
          className={classes.toolbarTitle}
        >
          CampaignBoys
        </Typography>
        <Box className={classes.menuButtons}>
          <Link
            className={classes.item}
            component={LinkRoute}
            to={"/"}
            variant="body2"
            color="textPrimary"
          >
            Home
          </Link>
          <Link
            className={classes.item}
            component={LinkRoute}
            variant="body2"
            color="textPrimary"
          >
            Contact
          </Link>

          {user.role === "COMPANY" ? (
            <Link
              className={classes.item}
              component={LinkRoute}
              to={"/company"}
              variant="body2"
              color="textPrimary"
            >
              Company Dashboard
            </Link>
          ) : null}
          {user.isLoggedIn ? (
            <Link
              className={classes.item}
              component={LinkRoute}
              onClick={() => handleLogout()}
              variant="body2"
              color="textPrimary"
            >
              Logout
            </Link>
          ) : (
            <>
              <Link
                className={classes.item}
                to={"/login"}
                component={LinkRoute}
                variant="body2"
                color="textPrimary"
              >
                Login
              </Link>
              <Link
                className={classes.item}
                to={"/Signup"}
                component={LinkRoute}
                variant="body2"
                color="textPrimary"
              >
                Sign up
              </Link>
            </>
          )}
        </Box>
      </Toolbar>
    </Container>
    // </div>
  );
}
