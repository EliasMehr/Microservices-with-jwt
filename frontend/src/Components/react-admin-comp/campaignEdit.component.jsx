import { Edit } from "react-admin";
import EditForm from "./editForm";
import React from "react";

export const CampaignEdit = (props) => {
  const transform = async (data) => {
    if (!data.image) {
      return {
        ...data,
      };
    }

    if(data.image) {
    const promiseImgToBase64 = await readFileAsDataURL(data.image);
    console.log(promiseImgToBase64);
    if (promiseImgToBase64) {
      return {
        ...data,
        image: promiseImgToBase64,
      };
    }
    }
  };

  async function readFileAsDataURL(file) {
    let result_base64 = await new Promise((resolve) => {
    if(typeof file === "string") {
        return resolve(file);
    }
      const fileReader = new FileReader();
      fileReader.onload = (e) => resolve(fileReader.result);
      fileReader.readAsDataURL(file.rawFile);
    

    });
    

    return result_base64;
  }

  return (
    <Edit {...props} transform={transform}>
      <EditForm redirect="list" />
    </Edit>
  );
};
