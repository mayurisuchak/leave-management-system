/* Create database **************************************************************************/
USE master
DROP DATABASE LeaveManagementSystem 
CREATE DATABASE LeaveManagementSystem
GO
USE LeaveManagementSystem
GO
/*************************************************************************** Create Table **************************************************************************/
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
/*************************************************************************** Create View **************************************************************************/
/* Create view leave full detail ************************************************************/
CREATE VIEW LeaveFullDetail
AS
SELECT Leave.*, [User].SuperiorID
	FROM [User], Leave
	WHERE Leave.UserID = [User].UserID
GO
/* Create view subordinate detail ***********************************************************/
CREATE VIEW SubordinateDetail
AS
SELECT SuperiorID, [User].UserID AS Code, Fullname AS Name, Position.LeaveDays AS [Total leave Days], (Position.LeaveDays - LeaveDays.LeaveDays) AS [Remaining leave days]
	FROM [User], Position, 
		(SELECT UserID, SUM(DATEDIFF(dy,DateStart,DateEnd)+1) AS LeaveDays
			FROM Leave
			GROUP BY UserID
		) AS LeaveDays
	WHERE Position.PositionID = [User].PositionID
GO
/*************************************************************************** Create Procedure View **************************************************************************/
/* Create procedure view submited leave detail application **********************************/
CREATE PROCEDURE sp_SubmitedLeaves
	@SuperiorID INT
AS
SELECT * 
	FROM LeaveFullDetail
	WHERE SuperiorID = @SuperiorID AND YEAR(DateStart) LIKE YEAR(GETDATE())
GO
/* Create procedure view leave detail application *******************************************/
CREATE PROCEDURE sp_LeaveDetail
	@UserID INT,
	@Year INT
AS
SELECT [Subject], DateStart AS [From], DateEnd AS [To], [State] AS [Status]
	FROM Leave
	WHERE UserID = @UserID AND YEAR(DateStart) LIKE @Year
GO
/* Create procedure view subordinate detail *************************************************/
CREATE PROCEDURE sp_SubordinateDetail
	@UserID INT
AS
SELECT * 
	FROM SubordinateDetail
	WHERE SuperiorID = @UserID
GO
/*************************************************************************** Create Procedure Modify **************************************************************************/
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
		@LeaveID
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
/* Create procedure withdraw/cancel leave ***************************************************/
CREATE PROCEDURE sp_CancelLeave
	@LeaveID INT
AS
BEGIN
UPDATE Leave
	SET [State] = 'Withdrawed'
	WHERE LeaveID = @LeaveID AND [State] = 'Not Approved'
IF (@@ROWCOUNT = 0)
	UPDATE Leave
		SET [State] = 'Canceling'
		WHERE LeaveID = @LeaveID AND [State] = 'Approved'
END
GO
/* Create procedure approve/reject leave/request ********************************************/
CREATE PROCEDURE sp_ManageRequest 
	@LeaveID INT,
	@Allowance BIT
AS
BEGIN
IF (@Allowance = 1)
	UPDATE Leave 
		SET [State] = 'Approved'
		WHERE LeaveID = @LeaveID AND [State] = 'Not Approved'
ELSE
	UPDATE Leave 
		SET [State] = 'Rejected'
		WHERE LeaveID = @LeaveID AND [State] = 'Not Approved'

IF (@@ROWCOUNT = 0)
	IF (@Allowance = 1)
	UPDATE Leave 
		SET [State] = 'Canceled'
		WHERE LeaveID = @LeaveID AND [State] = 'Canceling'
	ELSE
		UPDATE Leave 
			SET [State] = 'Cancel-Rejected'
			WHERE LeaveID = @LeaveID AND [State] = 'Canceling'
END
GO
