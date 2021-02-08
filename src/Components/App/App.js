import React, {useContext, useEffect} from 'react';
import Copyright from '../copyright/copyright.component';
import Home from '../../pages/Home';
import Login from '../../pages/Login';
import Signup from '../../pages/Signup';
import Company from '../../pages/Company';
import Email from '../../pages/Email';
import Navbar from '../navbar/navbar.component';
import {Switch, Route} from 'react-router-dom';
import { UserContext } from '../../context/UserContext';
import authService from '../../services/authService';
import { PrivateRoute } from '../routes/privateroute.component';



function App() {
  const [user, userDispatch] = useContext(UserContext);

    useEffect(() => {
        authService.currentUser.subscribe(x => {
            if(x !== null) {
            userDispatch({type: "LOAD_USER", payload: x})
            }
        })
        
    }, [] )

  
  return (
    <div>
      <Navbar/>
      <Switch>
        <Route exact path="/" component={Home} />
        <Route path="/verify/email" component={Email}/>
        <Route path="/login" component={Login} />
        <Route path="/signup" component={Signup} />
        <PrivateRoute path="/company" component={Company} roles={[user.role]}/>
        
      </Switch>
      <Copyright/>

    </div>
  )
}

export default App;