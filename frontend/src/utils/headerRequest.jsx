import authService from "../services/authService";

export default function headerRequest() {
  const currentUser = authService.currentUserValue;

  if (currentUser && currentUser.token) {
    return { Authorization: `Bearer ${currentUser.token}` };
  } else {
    return {};
  }
}
