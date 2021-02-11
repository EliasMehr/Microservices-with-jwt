import React from "react";
import Dialog from "@material-ui/core/Dialog";
import Button from "@material-ui/core/Button";
import InputAdornment from "@material-ui/core/InputAdornment";
import TextField from "@material-ui/core/TextField";
import AssignmentIcon from "@material-ui/icons/Assignment";
import { withStyles, makeStyles } from "@material-ui/core/styles";
import Card from "@material-ui/core/Card";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import CardMedia from "@material-ui/core/CardMedia";
import Typography from "@material-ui/core/Typography";
import StandardImg from "../../../resources/campaignMicro.png";

const DarkerDisabledTextField = withStyles({
  root: {
    marginRight: 8,
    "& .MuiInputBase-root.Mui-disabled": {
      color: "rgba(0, 0, 0, 0.8)", // (default alpha is 0.38)
    },
  },
})(TextField);

const useStyles = makeStyles({
  root: {
    maxWidth: 340,
  },
  image: {
    height: 140,
    opacity: 0.8,
  },
  buttons: {
    justifyContent: "center",
  },
  discountCode: {
    justifyContent: "center",
    display: "flex",
  },
  textInModal: {
    marginTop: 10,
    marginBottom: 10,
  },
  // companyName: {
  //     transform: 'Translate(-50%, -50%)',
  //     textAlign: 'center',
  //     top: 130,
  //     left: '50%',
  //     fontWeight: 'bold',
  //     position: 'absolute'
  // },
  dialog: {},
});

const CampaignModal = (props) => {
  const classes = useStyles();

  const handleRedirect = () => {
    navigator.clipboard.writeText(props.discountCode);
    props.onHide();
  };

  const handleClose = () => {
    props.onHide();
  };

  return (
    <>
      <Dialog
        onClose={handleClose}
        aria-labelledby="dialog-title"
        open={props.show}
        className={classes.dialog}
      >
        <Card className={classes.root}>
          <CardMedia
            className={classes.image}
            component="img"
            alt="company image"
            image={
              props.campaign.image
                ? "data:image/png;base64," + props.campaign.image
                : StandardImg
            }
            title="company imageurur"
          />
          <CardContent className={classes.cardContent}>
            <Typography
              align="center"
              variant="h5"
              fontWeight="bold"
              className={classes.companyName}
            >
              {props.campaign.company_name}
            </Typography>
            <Typography
              align="center"
              variant="h4"
              className={classes.textInModal}
            >
              {props.campaign.title}
            </Typography>
            <Typography align="center" className={classes.textInModal}>
              {props.campaign.discount}
              {props.campaign.isPercentage === true
                ? " % Discount"
                : props.campaign.currency + " Discount"}
            </Typography>
            <Typography className={classes.textInModal} align="center">
              Description???
              {props.campaign.description}
            </Typography>
            <div className={classes.discountCode}>
              <DarkerDisabledTextField
                id="input-with-icon-textfield"
                label="Discount code"
                align="center"
                InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">
                      <AssignmentIcon />
                    </InputAdornment>
                  ),
                }}
                disabled
                value={props.discountCode}
                variant="outlined"
              />
            </div>
          </CardContent>
          <CardActions className={classes.buttons}>
            <Button
              variant="contained"
              color="primary"
              onClick={handleRedirect}
            >
              Copy discount & Close
            </Button>
          </CardActions>
        </Card>
      </Dialog>
    </>
  );
};

export default CampaignModal;
