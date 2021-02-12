import { Create } from "react-admin";

import React from "react";
import CreateForm from "./createForm.component";

export const CampaignCreate = (props) => {
  const transform = async (data) => {
    if (!data.image) {
      return {
        ...data,
      };
    }
    const promiseImgToBase64 = await readFileAsDataURL(data.image);
    console.log(promiseImgToBase64.length);
    if (promiseImgToBase64) {
      return {
        ...data,
        image: promiseImgToBase64,
      };
    }
  };

  async function readFileAsDataURL(file) {
    let result_base64 = await new Promise((resolve) => {
      let fileReader = new FileReader();
      fileReader.onload = (e) => resolve(fileReader.result);
      fileReader.readAsDataURL(file.rawFile);
    });

    console.log(result_base64);

    return result_base64;
  }

  return (
    <Create {...props} transform={transform} >
      <CreateForm redirect="list" submitOnEnter={false}  />
    </Create>
  );
};
