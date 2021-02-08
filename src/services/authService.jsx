import axios from 'axios';
import {BehaviorSubject} from 'rxjs';
import cookies from 'js-cookie';


const currentUserSubject = new BehaviorSubject({
    token: cookies.get('token'),
    role: cookies.get('role')
});

const authService = {
    login,
    logout,
    registerCompany,
    registerCustomer,
    currentUser: currentUserSubject.asObservable(),
    get currentUserValue () { return currentUserSubject.value }
}

function registerCompany(companyUser) {
        console.log(companyUser.email);
        return axios.post("http://localhost:8080/user/register/company", {
             name: companyUser.name,
             email: companyUser.email,
             password: companyUser.password,
             address: companyUser.address,
             city: companyUser.city,
             zipCode: companyUser.zipCode,
             phoneNumber: companyUser.phoneNumber,
             organizationNumber: companyUser.organizationNumber,
             companyType: companyUser.companyType
        });
    }

function registerCustomer(customerUser) {
        console.log(customerUser.email);
        return axios.post("http://localhost:8080/user/register/customer", {
             firstName: customerUser.firstName,
             lastName: customerUser.lastName,
             email: customerUser.email,
             password: customerUser.password,
             address: customerUser.address,
             city: customerUser.city,
             zipCode: customerUser.zipCode,
             phoneNumber: customerUser.phoneNumber,
             personalIdNumber: customerUser.personalIdNumber
        });
    }


function login(username, password) {
        return axios.post("http://localhost:8080/login", {
            username, password
        }).then(res => {
            console.log(res.data.token);
            cookies.set('token', res.data.token, {path: '', expires: 7} )
            cookies.set('role', res.data.role, {path: '', expires: 7} )
            currentUserSubject.next(res.data);
            console.log("in auth api " + cookies.get('token'))
            return res.data;
        });
    }

function logout() {
    cookies.remove('token');
    cookies.remove('role');
    currentUserSubject.next(null);
}
export default authService;