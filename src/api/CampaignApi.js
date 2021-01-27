import axios from 'axios';
import Cookies from 'universal-cookie';

const cookies = new Cookies();


class CampaignApi {

    getAllPublishedCampaigns() {
        return axios.get("http://localhost:8080/campaign/all/published")
        .then(res => res.data)
        .catch(err => {
            console.log("ERROR");
            return err;
        })
    }

    getOneCampaign(campaignId) {
        console.log("in camp api" + cookies.get('token'))
        return axios.get("http://localhost:8080/campaign/" + campaignId, {
           headers: {
               Authorization: "Bearer " + cookies.get('token')
           }
        })
        .then(res => res.data)
        .catch(err => {
            console.log("ERROR");
            return err;
        })
    }



}

export default new CampaignApi()