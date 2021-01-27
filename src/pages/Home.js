import React, {useState, useEffect, useRef} from 'react'
import { makeStyles } from '@material-ui/core/styles';
import CampaignApi from '../api/CampaignApi';
import CampaignList from '../Components/campaign/cardList/campaignCardList.component'
import HeaderImage from '../Components/header/header.component'
import Container from '@material-ui/core/Container'

const useStyles = makeStyles({
   
  });
  

export default function Home  () { 
    const classes = useStyles();
    const [campaigns, setCampaigns] = useState([]);

    const didRun = useRef(false);

     
    useEffect(() => {

        if(didRun.current) {
            return;
        }
        
        didRun.current = true;
        CampaignApi.getAllPublishedCampaigns().then(campaigns => {
            console.log(campaigns);
            setCampaigns(campaigns);
            })
    }, [] )



    return (
        <>
         <HeaderImage/>
         <CampaignList campaigns={campaigns}/>
        </>
) }

