import { makeStyles } from "@material-ui/core/styles";
import Paper from "@material-ui/core/Paper";
import Typography from "@material-ui/core/Typography";

const useStyles = makeStyles((theme) => ({
  bigImage: {
    position: "relative",
    backgroundImage:
      "url(https://images.wallpaperscraft.com/image/cat_eyes_blue_143433_3840x2160.jpg)",
    backgroundSize: "cover",
    backgroundRepeat: "no-repeat",
    backgroundPosition: "center",
    height: "30vh",
  },
  overlay: {
    position: "absolute",
    top: 0,
    bottom: 0,
    right: 0,
    left: 0,
    backgroundColor: "rgba(0,0,0,.3)",
  },
  imageText: {
    textAlign: "center",
    top: "60%",
    left: "50%",
    position: "relative",
    transform: "Translate(-50%, -50%)",
    opacity: 0.6,
    fontWeight: "bold",
    color: "white",
  },
}));

function HeaderImage(props) {
  const classes = useStyles();

  return (
    <Paper className={classes.bigImage}>
      <div className={classes.overlay} />
      <Typography variant="h3" className={classes.imageText}>
        {props.headerText}
      </Typography>
    </Paper>
  );
}

export default HeaderImage;
