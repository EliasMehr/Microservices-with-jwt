import React from 'react';
import Copyright from '../copyright/copyright.component'
import Home from '../../pages/Home'
import Login from '../../pages/Login'
import Signup from '../../pages/Signup'
import Customer from '../../pages/Customer'
import Navbar from '../navbar/navbar.component'
import {Switch, Route} from 'react-router-dom';


function App() {
  return (
    <div>
      <Navbar/>
      <Switch>
        <Route exact path="/" component={Home} />
        <Route path="/login" component={Login} />
        <Route path="/signup" component={Signup} />
        <Route path="/customer" component={Customer} />
      </Switch>
      <Copyright/>

    </div>
  )
}

export default App;