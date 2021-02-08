import React from 'react'
import HeaderImage from '../Components/header/header.component';
import { Admin, Resource, fetchUtils } from 'react-admin';
// import jsonServerProvider from 'ra-data-json-server';
import jsonServerProvider from 'ra-data-simple-rest';
import headerRequest from '../utils/headerRequest';
import { makeStyles } from '@material-ui/core/styles';
import { CampaignList } from '../Components/react-admin-comp/campaignList.component';
import { CampaignCreate } from '../Components/react-admin-comp/campaignCreate.component';
import { CampaignEdit } from '../Components/react-admin-comp/campaignEdit.component';


const useStyles = makeStyles((theme) => ({
    root: {
        marginRight: 8,
        "& .MuiDrawer-paperAnchorDockedLeft": {
          display: 'none',
        },
      }
  }));
  

const Company = () => {
    const classes = useStyles();

     
        const httpClient = (url, options = {}) => {
            // if (!options.headers) {
                options.headers = new Headers({ Accept: 'application/json' });
            // }
            const token = headerRequest().Authorization;
            console.log("HEADER: " + token)
            options.headers.set('Authorization', token);
            return fetchUtils.fetchJson(url, options);
        }

   return ( 
    <>
        <HeaderImage headerText="Handle Your Campaigns"/>
        <div className={classes.root}>
        <Admin dataProvider={jsonServerProvider('http://localhost:8080', httpClient, 'X-Total-Count')}>
            <Resource name="campaign" list={CampaignList} edit={CampaignEdit} create={CampaignCreate}/>
        </Admin>
        </div>
        {console.log("IN COMPANY??")}
    </>
   )
}

export default Company;