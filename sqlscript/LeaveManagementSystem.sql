/* Create database **************************************************************************/
USE master
DROP DATABASE LeaveManagementSystem 
CREATE DATABASE LeaveManagementSystem
GO
USE LeaveManagementSystem
GO
/* Create table Position ********************************************************************/
CREATE TABLE Position(
	PositionID INT IDENTITY,
	PositionName VARCHAR(15) NOT NULL,
	LeaveDays INT NOT NULL,
	CONSTRAINT PK_Position_PositionID PRIMARY KEY (PositionID)
)
GO
/* Create table User ************************************************************************/
CREATE TABLE [User](
	UserID INT IDENTITY,
	Username VARCHAR(30) NOT NULL UNIQUE,
	[Password] VARCHAR(50) NOT NULL,
	Fullname VARCHAR(30) NOT NULL,
	SuperiorID INT NOT NULL,
	PositionID INT NOT NULL,
	CONSTRAINT PK_User_UserID PRIMARY KEY (UserID),
	CONSTRAINT FK_User_SuperiorID FOREIGN KEY (SuperiorID) REFERENCES [User](UserID),
	CONSTRAINT FK_User_PositionID FOREIGN KEY (PositionID) REFERENCES Position(PositionID)
)
GO
/* Create table Leave ***********************************************************************/
CREATE TABLE Leave(
	LeaveID INT IDENTITY,
	UserID INT NOT NULL,
	DateStart DATETIME NOT NULL,
	DateEnd DATETIME NOT NULL,
	[State] VARCHAR(30) NOT NULL,	/* 'Not Approved', 'Approved', 'Rejected', 'Withdrawed', 'Canceling', 'Cancel-Rejected', 'Canceled' */
	Reason VARCHAR(100) NOT NULL,
	Communication VARCHAR(50) NOT NULL,
	CurrentLeaveDays INT NOT NULL,
	[Date] DATETIME NOT NULL,
	[Subject] VARCHAR(30) NOT NULL,
	CONSTRAINT PK_Leave_LeaveID PRIMARY KEY (LeaveID),
	CONSTRAINT FR_Leave_UserID FOREIGN KEY (UserID) REFERENCES [User](USerID)
)
GO
/* Create table Log *************************************************************************/
CREATE TABLE [LOG](
	LogID INT IDENTITY,
	UserID INT NOT NULL,
	[Time] DATETIME NOT NULL UNIQUE,
	[Action] VARCHAR(30) NOT NULL,
	LeaveID INT NOT NULL,
	CONSTRAINT PK_Log_LogID PRIMARY KEY (LogID),
	CONSTRAINT FR_Log_UserID FOREIGN KEY (UserID) REFERENCES [User](UserID),
	CONSTRAINT FR_Log_LeaveID FOREIGN KEY (LeaveID) REFERENCES Leave(LeaveID)
)
GO
/* Create procedure change password *********************************************************/
CREATE PROCEDURE sp_ChangePassword 
	@UserID INT,
	@Password VARCHAR(50)
AS
UPDATE [User] 
	SET Password = @Password 
	WHERE UserID = @UserID
GO
/* Create procedure create new log **********************************************************/
CREATE PROCEDURE sp_CreateLog 
	@UserID INT,
	@Time DATETIME,
	@Action VARCHAR(30),
	@LeaveID INT = NULL
AS
INSERT INTO [LOG] 
	VALUES(
		@UserID,
		@Time,
		@ACtion,
		LeaveID
	)
GO
/* Create procedure create new leave application ********************************************/
CREATE PROCEDURE sp_ApplyLeave
	@UserID INT,
	@DateStart DATETIME,
	@DateEnd DATETIME,
	@Reason VARCHAR(100),
	@Communication VARCHAR(50),
	@Date DATETIME,
	@Subject VARCHAR(30)
AS
BEGIN
	DECLARE @CurrentLeaveDays INT
	SELECT @CurrentLeaveDays = Position.LeaveDays
		FROM Position, [User]
		WHERE Position.PositionID = [User].PositionID AND [User].UserID = @UserID
	INSERT INTO Leave
		VALUES(
			@UserID,
			@DateStart,
			@DateEnd,
			'Not Approved',	
			@Reason,
			@Communication,
			@CurrentLeaveDays,
			@Date,
			@Subject
		)
END
GO
