import React from "react";
import { UserReducer } from "../reducers/UserReducer";

export const UserContext = React.createContext();

const initialState = {
  isLoggedIn: false,
  role: "",
};

export const UserProvider = (props) => {
  const [user, userDispatch] = React.useReducer(UserReducer, initialState);

  return (
    <UserContext.Provider value={[user, userDispatch]}>
      {props.children}
    </UserContext.Provider>
  );
};
