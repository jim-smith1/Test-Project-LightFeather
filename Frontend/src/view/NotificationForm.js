import React, { useState, useEffect } from "react";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "./NotificationApp.css";
import { fetchSupervisors, submitFormData } from "../service/NotificationApi";

const NotificationForm = () => {
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    phoneNumber: "",
    notificationPreference: "",
    supervisor: "",
  });

  const [supervisors, setSupervisors] = useState([]);

  useEffect(() => {
    fetchSupervisorsData();
  }, []);

  const fetchSupervisorsData = async () => {
    try {
      const data = await fetchSupervisors();
      setSupervisors(data);
    } catch (error) {
      toast.error("Error fetching supervisors. Please try again.");
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({ ...prevState, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await submitFormData(formData);
      toast.success("Form submitted successfully.");
      setFormData({
        firstName: "",
        lastName: "",
        email: "",
        phoneNumber: "",
        notificationPreference: "",
        supervisor: "",
      });
    } catch (error) {
      if (error.response && error.response.data) {
        toast.error("Error: " + error.response.data);
      } else {
        toast.error("Error submitting form. Please try again.");
        console.error("Error submitting form:", error);
      }
    }
  };

  return (
    <>
      <Form className="notification-form" onSubmit={handleSubmit}>
        <ToastContainer />
        <div className="form-header">
          <h2>Notification Form</h2>
        </div>
        <div className="p-3">
          <Row className="mb-3">
            <Form.Group as={Col} controlId="formGridFirstName">
              <Form.Label>First Name</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter first name"
                name="firstName"
                value={formData.firstName}
                onChange={handleChange}
              />
            </Form.Group>

            <Form.Group as={Col} controlId="formGridLastName">
              <Form.Label>Last Name</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter last name"
                name="lastName"
                value={formData.lastName}
                onChange={handleChange}
              />
            </Form.Group>
          </Row>
          <Row className="mb-3">
            <Form.Label>How would you prefer to be notified?</Form.Label>
            <Form.Group as={Col} controlId="formGridEmail">
              <Form.Check
                type="checkbox"
                label="Email"
                name="notificationPreference"
                value="Email"
                onChange={handleChange}
              />
            </Form.Group>
            <Form.Group as={Col} controlId="formGridPhone">
              <Form.Check
                type="checkbox"
                label="Phone Number"
                name="notificationPreference"
                value="Phone Number"
                onChange={handleChange}
              />
            </Form.Group>
          </Row>
          <Row className="mb-3">
            <Form.Group as={Col} controlId="formGridEmailAddress">
              <Form.Control
                type="email"
                placeholder="Enter email address"
                name="email"
                value={formData.email}
                onChange={handleChange}
              />
            </Form.Group>

            <Form.Group as={Col} controlId="formGridPhoneNumber">
              <Form.Control
                type="text"
                placeholder="Enter phone number"
                name="phoneNumber"
                value={formData.phoneNumber}
                onChange={handleChange}
              />
            </Form.Group>
          </Row>
          <Row className="mb-3 justify-content-center">
            <Col xs={4}>
              <Form.Group>
                <Form.Label>Supervisor</Form.Label>
                <Form.Select
                  name="supervisor"
                  value={formData.supervisor}
                  onChange={handleChange}
                  style={{ color: formData.supervisor ? "black" : "gray" }}
                >
                  <option key="default" value="">
                    Choose...
                  </option>
                  {supervisors.map((supervisor, index) => {
                    const firstName = supervisor
                      .split(" - ")[1]
                      .split(",")[0]
                      .trim();
                    return (
                      <option key={index} value={firstName}>
                        {firstName}
                      </option>
                    );
                  })}
                </Form.Select>
              </Form.Group>
            </Col>
          </Row>
          <Row className="mb-3 justify-content-center">
            <Col xs={4}>
              <Button variant="primary" className="w-100" type="submit">
                Submit
              </Button>
            </Col>
          </Row>
        </div>
      </Form>
    </>
  );
};

export default NotificationForm;
