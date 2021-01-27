import axios from 'axios';
import Cookies from 'universal-cookie';

class AuthApi {
    login(username, password) {
        return axios.post("http://localhost:8080/login", {
            username, password
        }).then(res => {
            console.log(res.data.token);
            // Cookies.set('token', JSON.stringify(res.data.token), { expires: 7, sameSite: 'None', secure: true})
            const cookies = new Cookies();
            cookies.set('token', JSON.stringify(res.data.token), {path: '/'} )
            console.log("in auth api " + cookies.get('token'))
            return res.data;
        }).catch(err => {
            console.log(err)
            return err;
        })
    }
}
export default new AuthApi()