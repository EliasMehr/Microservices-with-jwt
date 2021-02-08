import React from 'react';
import Button from '@material-ui/core/Button';
import InputAdornment from '@material-ui/core/InputAdornment';
import TextField from '@material-ui/core/TextField';
import AssignmentIcon from '@material-ui/icons/Assignment';
import { withStyles, makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card'
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Typography from '@material-ui/core/Typography';
import StandardImage from '../../resources/campaignMicro.png';

const DarkerDisabledTextField = withStyles({
    root: {
      marginRight: 8,
      "& .MuiInputBase-root.Mui-disabled": {
        color: "rgba(0, 0, 0, 0.8)" // (default alpha is 0.38)
      },
    }
  })(TextField);

const useStyles = makeStyles({
    root: {
        maxWidth: 340
    },
    image: {
        height:140,
    },
    buttons: {
        justifyContent: 'center',
    },
    discountCode: {
        justifyContent: 'center',
        display: 'flex'
    },
    textInModal: {
        marginTop: 10,
        marginBottom: 10
    },

});


const CreateCampaignCard = props => {
    const classes = useStyles();


    return (
        <>
            <Card className={classes.root}>
                {console.log(StandardImage)}
                <CardMedia
                    className={classes.image}
                    component="img"
                    alt="company image"
                    src={props.image ? props.image : StandardImage}
                    title="company imageurur"
                    />
                <CardContent className={classes.cardContent}>
                    <Typography 
                        align='center'
                        variant='h5' 
                        fontWeight='bold'
                        className={classes.companyName}                   
                        >
                        Company Name            
                    </Typography>
                    <Typography 
                        align='center'
                        variant='h4'
                        className={classes.textInModal}
                        >
                        {props.title ? props.title : "Example Title"}            
                    </Typography>
                    <Typography 
                        align='center'
                        className={classes.textInModal}
                        >
                        {props.discount ? props.discount : "Discount amount"}
                        {props.isPercentage ? ' % Discount' : ' SEK Discount'}
                    </Typography>
                    <Typography 
                        className={classes.textInModal}
                        align='center'
                        >
                        {props.description ? props.description : 'Description here..'}
                    </Typography>
                        <div className={classes.discountCode}>
                        <DarkerDisabledTextField
                        id="input-with-icon-textfield"
                        label="Discount code"
                        align='center'
                        InputProps={{
                            startAdornment: (
                                <InputAdornment position="start">
                                <AssignmentIcon />
                                </InputAdornment>
                            ),
                        }}
                        disabled
                        value={props.discountCode ? props.discountCode : 'Discount Code'}
                        variant='outlined'
                    />
                    </div>
                </CardContent>
                <CardActions className={classes.buttons}>
                    <Button variant="contained" color="primary">
                         Copy discount & Close
                    </Button>
                </CardActions>
            </Card>
        


        </>
    )
}

export default CreateCampaignCard;