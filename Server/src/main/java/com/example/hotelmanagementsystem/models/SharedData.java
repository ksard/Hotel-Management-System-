package com.example.hotelmanagementsystem.models;

public class SharedData {
    //region Status Code
    public static final int _SuccessCode = 0;
    public static final int _NotFound = 2404;
    public static final int _PartialCode = 2206;
    public static final int _BadRequestCode = 2204;
    public static final int _FailedCode = 2500;
    //endregion

    //region Status Message
    public static final String _SuccessMeg = "Booking Successful.";
    public static final String _FailedMeg = "Booking Failed";
    public static final String _SuccessBookingCancelMeg = "Booking Successfully Canceled";
    public static final String _FailedBookingCancelMeg = "Failed to Cancel Booking";
    public static final String _SuccessCheckInMsg = "CheckIn Successful";
    public static final String _FailedCheckInMsg = "CheckIn Failed";
    public static final String _SuccessCheckOutMsg = "CheckOut Successful";
    public static final String _FailedCheckOutMsg = "CheckOut Failed";
    public static final String _SuccessRefundMsg = "Refund Successful";
    public static final String _FailedRefundMsg = "Refund Failed";
    public static final String _SuccessFetchBookingMsg = "Successfully Fetch All bookings";
    public static final String _SuccessFetchServicesMsg = "Successfully fetched all services";
    public static final String _FailedFetchBookingMsg = "Failed to Fetch All bookings";
    public static final String _SuccesRoomTypeFetch = "Successfully fetched all room types";
    public static final String _EmailExists = "Email already exists";
    public static final String _RegistrationFailed = "Registration failed";
    public static final String _RegistrationSuccessful = "Registration successful and user logged in";
    public static final String _RegSuccessfulLoginFailed = "Registration successful and user failed to log in";
    public static final String _SuccessRoomFetchMsg = "Successfully Fetch All rooms";
    public static final String _FailedRoomFetchMsg = "Failed to Fetch All rooms";
    public static final String _JWTExpired = "JWT was expired or incorrect";
    public static final String _ServicesAdded = "Services added successfully";
    public static final String _ServicesAdditionFailed= "Services addition failed";
    public static final String _CreatedHolidays= "Holidays created successfully";
    public static final String _FetchedHolidays= "Successfully Fetched All Holidays";
    public static final String _FailedFetchedHolidays= "Failed to Fetched All Holidays";
    public static final String _HolidaysCreationFailed= "Holidays creation failed";
    public static final String _RoomTypesServicesFetched= "Room types and services fetched";

    //region status
    public static final String _Success = "success";
    public static final String _Failed = "failed";
    //endregion


    //region booking status
    public static final String _Confirmation = "Confirmed";
    public static final String _Cancellation = "Cancelled";
    public static final String _Refund = "Refunded";
    public static final String _CheckIn = "CheckedIn";
    public static final String _CheckOut = "CheckedOut";
    public static final String _RefundInitiated = "RefundInitiated";

    //endregion

    //region user type
    public static final String _GuestType = "guest";
    public static final String _EmployeeType = "employee";

    //endregion

    //region request header
    public static final String _Authorization = "Authorization";
    public static final String _TokenType = "Bearer ";
    //endregion


}
