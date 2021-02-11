import React, { useState, useEffect, useRef } from "react";
import CampaignList from "../Components/campaign/cardList/campaignCardList.component";
import HeaderImage from "../Components/header/header.component";
import campaignService from "../services/campaignService";

export default function Home() {
  const [campaigns, setCampaigns] = useState([]);

  const didRun = useRef(false);

  useEffect(() => {
    if (didRun.current) {
      return;
    }

    didRun.current = true;
    campaignService.getAllPublishedCampaigns().then((campaigns) => {
      setCampaigns(campaigns);
    });
  }, []);

  return (
    <>
      <HeaderImage headerText="Special deals, just for you (and everyone else)" />
      <CampaignList campaigns={campaigns} />
    </>
  );
}
