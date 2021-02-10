import React, {useState} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid'
import Card from '@material-ui/core/Card'
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import CampaignModal from '../modal/campaignModal.component';
import { useHistory } from 'react-router-dom';
import Collapse from '@material-ui/core/Collapse';
import Link from '@material-ui/core/Link';
import campaignService from '../../../services/campaignService';
import StandardImage from '../../../resources/campaignMicro.png'


const useStyles = makeStyles({
    root: {
        textAlign: 'center',
        minHeight: 400
    },
    image: {
        height:140,
    },
    buttons: {
        justifyContent: 'center',
    },
    description: {
        minHeight: 80
    }
  });

  
const CampaignCard = props => {
    const [campaignModalShow, setCampaignModalShow] = useState(false);
    const [collapseShow, setCollapseShow] = useState(false);
    const [discountCode, setDiscountCode] = useState("");
    let history = useHistory();

    const classes = useStyles();

    const handleClose = () => {
        setCampaignModalShow(false);
    }

    const handleOpen = () => {
        campaignService.getDiscountCode(props.campaign.id).then(res => {
            if(!res) {
                return history.push('/login');
            }
            setDiscountCode(res);
            setCampaignModalShow(true);
        });
        // setCampaignModalShow(true);
    }

    const handleCollapse = (e) => {
        e.preventDefault();
        collapseShow ? setCollapseShow(false) : setCollapseShow(true);

    }
   return (
       <>
        <Grid item xs={12} sm={3}>
            <Card className={classes.root}>
                <CardMedia
                    className={classes.image}
                    component="img"
                    alt="company image"
                    image={props.campaign.image  ? "data:image/png;base64," + props.campaign.image : StandardImage}
                    title="company imageurur"
                    />
                <CardContent>
                    <Typography 
                        align='center'
                        variant='h6'
                        >
                        {props.campaign.company_name}  
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
                        {props.campaign.isPercentage ? ' % Discount' : ' SEK Discount'}            
                    </Typography>
                        {props.campaign.description  ? 
                         props.campaign.description.length > 20 ? (
                         <>
                            <div className={classes.description}>
                            <Typography align='center' variant='h6'>Description</Typography>
                            <Typography align='center'>{props.campaign.description.substring(0, 20)}</Typography>
                            <Collapse in={collapseShow}>{props.campaign.description.substring(20)}</Collapse>
                            <Link href="#" onClick={handleCollapse}>Read more...</Link>
                            </div>
                            </>)  
                            : (
                                <>
                                <div className={classes.description}>
                                <Typography align='center' variant='h6'>Description</Typography>
                                <Typography align='center'>{props.campaign.description}</Typography>
                                </div>
                                </>
                            ) : 
                                <>
                                <div className={classes.description}>

                                </div>
                                </>
                            }


                       
                </CardContent>
                <CardActions className={classes.buttons}>
                    <Button variant="contained" color="primary" onClick={handleOpen}>
                        Get Discount
                    </Button>
                </CardActions>
            </Card>
        </Grid>

        <CampaignModal show={campaignModalShow} onHide={handleClose} campaign={props.campaign} discountCode={discountCode}/>
        </>
    )
}

export default CampaignCard;