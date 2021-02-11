import { useState, useEffect } from "react";
import {
  FormWithRedirect,
  TextInput,
  Toolbar,
  SaveButton,
  DeleteButton,
  DateTimeInput,
  SelectInput,
  BooleanInput,
  ImageInput,
  ImageField,
} from "react-admin";
import { Typography, Box } from "@material-ui/core";
import CreateCampaignCard from "./createCampaignCard.component";

const EditForm = (props) => {
  const [campaign, setCampaign] = useState({
    title: "",
    description: "",
    category: "",
    discount: "",
    publishedAt: "",
    expiresAt: "",
    isPercentage: "",
    image: "",
    discountCode: "",
  });

  useEffect(() => {
    console.log("HEJ");
    setCampaign(props.record);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);


  const handleChange = (prop) => (e) => {
    setCampaign({ ...campaign, [prop]: e.target.value });
  };

  const handleImage = (img) => {
    let reader = new FileReader();
    reader.onload = function (event) {
      setCampaign({ ...campaign, image: event.target.result });
    };
    reader.readAsDataURL(img[0]);
  };

  return (
    <FormWithRedirect
      {...props}
      render={(formProps) => (
        <form>
          <Box p="1em" display="flex" justifyContent="center">
            <Box display="flex">
              <Box flex={2} mr="1em">
                <Typography variant="h6" gutterBottom>
                  Campaign
                </Typography>

                <Box display="flex">
                  <Box flex={1} mr="0.5em">
                    <TextInput
                      source="title"
                      fullWidth
                      required
                      value={campaign.title}
                      onChange={handleChange("title")}
                    />
                  </Box>
                  <Box flex={1} ml="0.5em">
                    <TextInput
                      source="category"
                      fullWidth
                      required
                      value={campaign.category}
                      onChange={handleChange("category")}
                    />
                  </Box>
                </Box>

                <TextInput
                  source="description"
                  fullWidth
                  multiline
                  value={campaign.description}
                  onChange={handleChange("description")}
                />

                <ImageInput accept="image/*" source="image" options={{onDrop: image => handleImage(image)}}>
                  <ImageField source="url" title="Title or url" />
                </ImageInput>

                <Typography variant="h6" gutterBottom>
                  Discount
                </Typography>
                <Box display="flex">
                  <Box flex={1} mr="0.5em">
                    <TextInput
                      source="discount"
                      type="number"
                      fullWidth
                      required
                      value={campaign.discount}
                      onChange={handleChange("discount")}
                    />
                  </Box>
                  <Box>
                    <SelectInput
                      source="isPercentage"
                      label="Discount Type"
                      onChange={handleChange("isPercentage")}
                      choices={[
                        { id: true, name: "Percentage" },
                        { id: false, name: "SEK" },
                      ]}
                    />
                  </Box>
                  <Box flex={1} ml="0.5em">
                    <TextInput
                      source="discountCode"
                      fullWidth
                      required
                      value={campaign.discountCode}
                      onChange={handleChange("discountCode")}
                    />
                  </Box>
                </Box>
                <Box mt="1em" />

                <Box display="flex">
                  <Box flex={1} mr="0.5em">
                    <DateTimeInput
                      source="publishedAt"
                      fullWidth
                      value={campaign.publishedAt}
                      onChange={handleChange("publishedAt")}
                    />
                  </Box>
                  <Box flex={2} ml="0.5em">
                    <DateTimeInput
                      source="expiresAt"
                      fullWidth
                      value={campaign.expiresAt}
                      onChange={handleChange("expiresAt")}
                    />
                  </Box>
                </Box>
                <Box display="flex">
                  <Box flex={2} ml="0.5em">
                    <BooleanInput source="isPercentage" />
                  </Box>
                  <Box flex={2} ml="0.5em">
                    <BooleanInput source="isPublished" />
                  </Box>
                </Box>
              </Box>

              <Box flex={1} ml="1em">
                <CreateCampaignCard {...campaign} />
              </Box>
            </Box>
          </Box>
          <Toolbar>
            <Box display="flex" justifyContent="center" width="100%">
              <SaveButton
                saving={formProps.saving}
                handleSubmitWithRedirect={formProps.handleSubmitWithRedirect}
              />
              <DeleteButton record={formProps.record} />
            </Box>
          </Toolbar>
        </form>
      )}
    />
  );
};

export default EditForm;
