import React, { useState } from "react";
import Avatar from "@material-ui/core/Avatar";
import Button from "@material-ui/core/Button";
import CssBaseline from "@material-ui/core/CssBaseline";
import TextField from "@material-ui/core/TextField";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Checkbox from "@material-ui/core/Checkbox";
import Link from "@material-ui/core/Link";
import Grid from "@material-ui/core/Grid";
import Box from "@material-ui/core/Box";
import LockOutlinedIcon from "@material-ui/icons/LockOutlined";
import Typography from "@material-ui/core/Typography";
import { makeStyles } from "@material-ui/core/styles";
import Container from "@material-ui/core/Container";
import Radio from "@material-ui/core/Radio";
import RadioGroup from "@material-ui/core/RadioGroup";
import FormControl from "@material-ui/core/FormControl";
import FormLabel from "@material-ui/core/FormLabel";
import { Link as RouterLink, useHistory } from "react-router-dom";
import TextareaAutosize from "@material-ui/core/TextareaAutosize";
import { InputLabel, Select } from "@material-ui/core";
import MenuItem from "@material-ui/core/MenuItem";
import authService from "../services/authService";
import CircularProgress from "@material-ui/core/CircularProgress";

const useStyles = makeStyles((theme) => ({
  paper: {
    marginTop: theme.spacing(8),
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
  },
  avatar: {
    margin: theme.spacing(1),
    backgroundColor: theme.palette.secondary.main,
  },
  form: {
    width: "100%", // Fix IE 11 issue.
    marginTop: theme.spacing(3),
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
  },
  formControl: {
    margin: theme.spacing(1),
    minWidth: 120,
  },
}));

export default function SignUp() {
  const classes = useStyles();
  let history = useHistory();
  const [open, setOpen] = useState(false);
  const [value, setSelectedValue] = useState("");
  const [loading, setLoading] = useState(false);

  const [user, setUser] = useState({
    name: "",
    organizationNumber: "",
    companyType: "",
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    address: "",
    city: "",
    zipCode: "",
    phoneNumber: "",
    personalIdNumber: "",
  });

  const handleUserChange = (prop) => (e) => {
    setUser({ ...user, [prop]: e.target.value });
  };

  const handleChange = (event) => {
    setSelectedValue(event.target.value);
    console.log(event.target.value);
  };

  const attemptRegisterUser = (e) => {
    e.preventDefault();
    setLoading(true);
    if (value === "customer") {
      authService
        .registerCustomer(user)
        .then((res) => {
          if (res.status === 200) {
            setLoading(false);
            console.log("WE MADE IT?");
            history.push("/verify/email");
          } else {
            console.log("TODO HANDLE ERRORS?");
            setLoading(false);
          }
        })
        .catch((err) => {
          console.log(err);
          setLoading(false);
        });
    } else if (value === "company") {
      authService
        .registerCompany(user)
        .then((res) => {
          if (res.status === 200) {
            setLoading(false);
            console.log("COMPANY MADE IT?");
            history.push("/verify/email");
          } else {
            setLoading(false);
            console.log("TODO HANDLE ERRORS");
          }
        })
        .catch((err) => {
          console.log(err);
          setLoading(false);
        });
    } else {
      console.log("NO USER SELECTED");
      setLoading(false);
    }
  };

  return (
    <Container component="main" maxWidth="xs">
      <CssBaseline />
      <div className={classes.paper}>
        <Avatar className={classes.avatar}>
          <LockOutlinedIcon />
        </Avatar>
        <Typography component="h1" variant="h5">
          Sign up
        </Typography>
        <form className={classes.form} noValidate>
          <Grid container spacing={2}>
            <Grid item xs={12}>
              <TextField
                variant="outlined"
                required
                fullWidth
                value={user.email}
                onChange={handleUserChange("email")}
                id="email"
                label="Email Address"
                name="email"
                autoComplete="email"
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                variant="outlined"
                required
                fullWidth
                value={user.password}
                onChange={handleUserChange("password")}
                name="password"
                label="Password"
                type="password"
                id="password"
                autoComplete="current-password"
              />
            </Grid>
            <Grid item xs={12}>
              <FormControl component="fieldset">
                <FormLabel component="legend">Sign up as</FormLabel>
                <RadioGroup
                  row
                  aria-label="type"
                  name="type1"
                  value={value}
                  onChange={handleChange}
                >
                  <FormControlLabel
                    value="customer"
                    control={<Radio />}
                    label="Customer"
                  />
                  <FormControlLabel
                    value="company"
                    control={<Radio />}
                    label="Company"
                  />
                </RadioGroup>
              </FormControl>
            </Grid>
            {console.log(user)}
            {value === "customer" ? (
              <Grid container spacing={2}>
                <Grid item xs={12} sm={6}>
                  <TextField
                    autoComplete="fname"
                    name="firstName"
                    variant="outlined"
                    value={user.firstName}
                    onChange={handleUserChange("firstName")}
                    required
                    fullWidth
                    id="firstName"
                    label="First Name"
                    autoFocus
                  />
                </Grid>
                <Grid item xs={12} sm={6}>
                  <TextField
                    variant="outlined"
                    required
                    fullWidth
                    value={user.lastName}
                    onChange={handleUserChange("lastName")}
                    id="lastName"
                    label="Last Name"
                    name="lastName"
                    autoComplete="lname"
                  />
                </Grid>
                <Grid item xs={12} sm={6}>
                  <TextField
                    autoComplete="phoneNr"
                    name="phoneNr"
                    variant="outlined"
                    required
                    value={user.phoneNumber}
                    onChange={handleUserChange("phoneNumber")}
                    fullWidth
                    id="phoneNr"
                    label="Phone Number"
                  />
                </Grid>
                <Grid item xs={12} sm={6}>
                  <TextField
                    variant="outlined"
                    required
                    fullWidth
                    value={user.personalIdNumber}
                    onChange={handleUserChange("personalIdNumber")}
                    id="personalID"
                    label="Personal ID"
                    name="personalID"
                    autoComplete="personalID"
                  />
                </Grid>
                <Grid item xs={12} sm={8}>
                  <TextField
                    autoComplete="street"
                    name="street"
                    variant="outlined"
                    value={user.address}
                    onChange={handleUserChange("address")}
                    required
                    fullWidth
                    id="street"
                    label="Street"
                  />
                </Grid>
                <Grid item xs={12} sm={8}>
                  <TextField
                    variant="outlined"
                    required
                    fullWidth
                    value={user.city}
                    onChange={handleUserChange("city")}
                    id="city"
                    label="City"
                    name="city"
                    autoComplete="city"
                  />
                </Grid>
                <Grid item xs={12} sm={4}>
                  <TextField
                    variant="outlined"
                    required
                    fullWidth
                    value={user.zipCode}
                    onChange={handleUserChange("zipCode")}
                    id="zipCode"
                    label="Zip Code"
                    name="zipCode"
                    autoComplete="zipCode"
                  />
                </Grid>
              </Grid>
            ) : null}

            {value === "company" ? (
              <Grid container spacing={2}>
                <Grid item xs={12} sm={6}>
                  <TextField
                    autoComplete="companyName"
                    name="companyName"
                    variant="outlined"
                    value={user.name}
                    onChange={handleUserChange("name")}
                    required
                    fullWidth
                    id="companyName"
                    label="Company Name"
                    autoFocus
                  />
                </Grid>
                <Grid item xs={12} sm={6}>
                  <TextField
                    autoComplete="phoneNr"
                    name="phoneNr"
                    variant="outlined"
                    required
                    value={user.phoneNumber}
                    onChange={handleUserChange("phoneNumber")}
                    fullWidth
                    id="phoneNr"
                    label="Phone Number"
                  />
                </Grid>
                <Grid item xs={12} sm={6}>
                  <TextField
                    variant="outlined"
                    required
                    fullWidth
                    value={user.organizationNumber}
                    onChange={handleUserChange("organizationNumber")}
                    id="organizationNr"
                    label="Organization Numer"
                    name="organizationNr"
                    autoComplete="organizationNr"
                  />
                </Grid>
                <Grid item xs={12} sm={6}>
                  <TextField
                    variant="outlined"
                    required
                    fullWidth
                    value={user.city}
                    onChange={handleUserChange("city")}
                    id="city"
                    label="City"
                    name="city"
                    autoComplete="city"
                  />
                </Grid>
                <Grid item xs={12} sm={6}>
                  <TextField
                    autoComplete="street"
                    name="street"
                    variant="outlined"
                    required
                    value={user.address}
                    onChange={handleUserChange("address")}
                    fullWidth
                    id="street"
                    label="Street"
                  />
                </Grid>
                <Grid item xs={12} sm={4}>
                  <TextField
                    variant="outlined"
                    required
                    fullWidth
                    value={user.zipCode}
                    onChange={handleUserChange("zipCode")}
                    id="zipCode"
                    label="Zip Code"
                    name="zipCode"
                    autoComplete="zipCode"
                  />
                </Grid>
                <Grid item xs={12} sm={6}>
                  <TextareaAutosize
                    aria-label="minimum height"
                    rowsMin={5}
                    placeholder="Company description"
                  />
                </Grid>
                <Grid item xs={12} sm={6}>
                  <FormControl className={classes.formControl}>
                    <InputLabel id="select-label">Company Type</InputLabel>
                    <Select
                      labelId="select-label"
                      open={open}
                      onClose={() => setOpen(false)}
                      onOpen={() => setOpen(true)}
                      value={user.companyType}
                      onChange={handleUserChange("companyType")}
                    >
                      <MenuItem value={"RETAIL"}>Retail</MenuItem>
                      <MenuItem value={"TELECOM"}>Telecom</MenuItem>
                      <MenuItem value={"HEALTH"}>Health</MenuItem>
                      <MenuItem value={"RESTAURANT"}>Restaurant</MenuItem>
                      <MenuItem value={"TRANSPORTATION"}>
                        Transportation
                      </MenuItem>
                      <MenuItem value={"SOFTWARE"}>Software</MenuItem>
                      <MenuItem value={"OTHER"}>Other</MenuItem>
                    </Select>
                  </FormControl>
                </Grid>
              </Grid>
            ) : null}

            <Grid item xs={12}>
              <FormControlLabel
                control={<Checkbox value="allowExtraEmails" color="primary" />}
                label="I want to receive spam from Elias and updates via email."
              />
            </Grid>
          </Grid>
          {!loading ? (
            <Button
              type="submit"
              fullWidth
              variant="contained"
              color="primary"
              className={classes.submit}
              onClick={attemptRegisterUser}
            >
              Sign Up
            </Button>
          ) : (
            <CircularProgress />
          )}
          <Grid container justify="flex-end">
            <Grid item>
              <Link
                component={RouterLink}
                to={"/login"}
                href="#"
                variant="body2"
              >
                Already have an account? Sign in
              </Link>
            </Grid>
          </Grid>
        </form>
      </div>
      <Box mt={5}></Box>
    </Container>
  );
}
