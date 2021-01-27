import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import CampaignCard from '../card/campaignCard.component'
import Grid from '@material-ui/core/Grid'


const useStyles = makeStyles({
    gridContainer: {
        maxWidth: 1200,
        margin: 'auto',
        direction:'row',
        justify:'center',
        alignItems:'center',
        wrap: 'wrap'
    },
  });


const CampaignList = ({campaigns}) => {
    const classes = useStyles();


    return (
        <Grid container 
            className={classes.gridContainer}
            spacing='3'
            wrap
            >

           {campaigns.map(campaign => (
               <CampaignCard key={campaign.id} campaign={campaign}/>
           ))}
        </Grid>
    )
}

export default CampaignList;