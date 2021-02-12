import {
  List,
  Datagrid,
  TextField,
  DateField,
  BooleanField,
  EditButton,
} from "react-admin";

import { makeStyles } from "@material-ui/core/styles";

const useStyles = makeStyles(
  theme => ({
    Sidebar: {
      display: "none"
    }
   
  }));

export const CampaignList = (props) => (
  <List pagination={null} theme={useStyles} {...props}>
    <Datagrid>
      <TextField source="title" />
      <TextField source="description" />
      <TextField source="discount" />
      <TextField source="category" />
      <TextField source="discountCode" />
      <DateField source="createdAt" />
      <DateField source="publishedAt" />
      <DateField source="updatedAt" />
      <DateField source="expiresAt" />
      <BooleanField source="isPercentage" />
      <BooleanField source="isPublished" />
      <EditButton />
    </Datagrid>
  </List>
);
