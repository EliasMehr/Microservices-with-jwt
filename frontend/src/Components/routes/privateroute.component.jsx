import React from "react";
import { Route, Redirect } from "react-router-dom";
import AuthApi from "../../services/authService";

export const PrivateRoute = ({ component: Component, roles, ...rest }) => (
  <Route
    {...rest}
    render={(props) => {
      const currentUser = AuthApi.currentUserValue;
      if (!currentUser) {
        return <Redirect to={{ pathname: "/login" }} />;
      }

      if (roles && roles.indexOf(currentUser.role) === -1) {
        return <Redirect to={{ pathname: "/" }} />;
      }

      return <Component {...props} />;
    }}
  />
);
