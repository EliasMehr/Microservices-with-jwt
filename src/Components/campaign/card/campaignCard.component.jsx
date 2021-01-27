import React, {useState, useEffect} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid'
import Card from '@material-ui/core/Card'
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import CampaignModal from '../modal/campaignModal.component';
import CampaignApi from '../../../api/CampaignApi';


const useStyles = makeStyles({

    image: {
        height:140,
    },
    buttons: {
        justifyContent: 'center',
    },
  });

  
const CampaignCard = props => {
    const [campaignModalShow, setCampaignModalShow] = useState(false);
    // const [campaignObject, setCampaignObject] = useState({});
    const classes = useStyles();
    const standardImg = "https://images.theconversation.com/files/350865/original/file-20200803-24-50u91u.jpg?ixlib=rb-1.1.0&rect=37%2C29%2C4955%2C3293&q=45&auto=format&w=926&fit=clip";

    const handleClose = () => {
        setCampaignModalShow(false);
    }

    const handleOpen = () => {
        // CampaignApi.getOneCampaign(props.campaign.id).then(res => {
        //     setCampaignObject(res);
        //     setCampaignModalShow(true);
        // });
        setCampaignModalShow(true);
        console.log("SETTING STATE")
    }

   return (
       <>
        <Grid item xs={12} sm={3}>
            <Card className={classes.root}>
                <CardMedia
                    className={classes.image}
                    component="img"
                    alt="company image"
                    image={props.campaign.image === null ? standardImg : props.campaign.image}
                    title="company imageurur"
                    />
                <CardContent>
                    <Typography 
                        align='center'
                        variant='h6'
                        >
                         TODO NAME?   
                        {props.campaign.name}            
                    </Typography>
                    <Typography 
                        align='center'
                        variant='h4'
                        >
                        {props.campaign.title}            
                    </Typography>
                    <Typography 
                        align='center'
                        >
                        {props.campaign.discount}
                        {props.campaign.isPercentage === true ? ' % Discount' : props.campaign.currency + ' Discount'}            
                    </Typography>
                    <Typography 
                        align='center'
                        >
                        Description???
                        {props.campaign.description}
                    </Typography>
                </CardContent>
                <CardActions className={classes.buttons}>
                    <Button variant="contained" color="primary" onClick={handleOpen}>
                        Get Discount
                    </Button>
                </CardActions>
            </Card>
        </Grid>

        <CampaignModal show={campaignModalShow} onHide={handleClose} campaign={props.campaign}/>
        </>
    )
}

export default CampaignCard;