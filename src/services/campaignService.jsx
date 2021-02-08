import axios from 'axios';
import headerRequest from '../utils/headerRequest';


const campaignService = {
    getAllPublishedCampaigns,
    getDiscountCode,
    getAllCompanyCampaigns
};



function getAllPublishedCampaigns() {
        return axios.get("http://localhost:8080/campaign/all/published")
        .then(res => res.data)
        .catch(err => {
            console.log("ERROR");
            return err;
        })
    }

function getDiscountCode(campaignId) {
    console.log("in discount code " + new headerRequest().Authorization);
        return axios.get("http://localhost:8080/campaign/discount-code/" + campaignId, {
           headers: headerRequest()
        })
        .then(res => res.data)
        .catch(err => {
            console.log("ERROR" + err);
        })
    }

function getAllCompanyCampaigns() {
        return axios.get("http://localhost:8080/campaign",{
            headers: headerRequest()
         })
    }





export default campaignService;