export const UserReducer = (state, action) => {
  switch (action.type) {
    case "LOAD_USER":
      console.log(action.payload.role);
      return {
        isLoggedIn: action.payload.token ? true : false,
        role: action.payload.role ? action.payload.role : "",
      };
    case "LOGIN":
      return {
        isLoggedIn: action.payload.token ? true : false,
        role: action.payload.role ? action.payload.role : "",
      };
    case "LOGOUT":
      return {
        isLoggedIn: false,
        role: "",
      };
    default:
      return;
  }
};
