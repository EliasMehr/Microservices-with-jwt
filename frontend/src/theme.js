import { red } from '@material-ui/core/colors';
import { createMuiTheme } from '@material-ui/core/styles';
// A custom theme for this app
const theme = createMuiTheme({
  palette: {
    type: 'light',
    primary: {
      main: '#61dafb',
      light: '#61dafb',
      dark: '#21a1c4',
    },
    secondary: {
      main: '#b5ecfb',
      light: '#61dafb',
      dark: '#21a1c4',
    },
    error: {
      main: red.A400,
    },
    background: {
      default: '#f1f1f1',
    },
  },
  overrides: {
    MuiPaper: {
      root: {
        padding: '10px 10px',
        backgroundColor: '#fff', // 5d737e
      },
    },
    
    MuiButton: {
      root: {
        margin: '5px',
        background: "linear-gradient(45deg, #FE6B8B 30%, #FF8E53 90%)",
        border: 0,
        borderRadius: 3,
        boxShadow: "0 3px 5px 2px rgba(255, 105, 135, .3)",
        color: "white",
        height: 48,
        padding: "0 30px",        
      },
    },
    MuiCard: {
      root: {
        minHeight: '400px',
        width: '100%',
      }
    },
    MuiCardMedia: {
      img: {
        padding: '-20px -10px', 
      }
    }
  },
});
export default theme;