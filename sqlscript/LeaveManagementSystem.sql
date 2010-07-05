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
	PositionName VARCHAR(30) NOT NULL,
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
	[Date] DATETIME NOT NULL,
	[Subject] VARCHAR(30) NOT NULL,
	CONSTRAINT PK_Leave_LeaveID PRIMARY KEY (LeaveID),
	CONSTRAINT FR_Leave_UserID FOREIGN KEY (UserID) REFERENCES [User](USerID)
)
GO
/* Create table Log *************************************************************************/
CREATE TABLE [Log](
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
/* Create view log detail *******************************************************************/
CREATE VIEW LogDetail
AS
SELECT [Log].UserID, [Log].LeaveID, Time, [User].Username, 'Leave "' + Leave.[Subject] + '": ' + [Log].[Action] as [Action]
	FROM [Log] INNER JOIN [User] ON ([Log].UserID = [User].UserID) LEFT JOIN Leave ON ([Log].LeaveID = Leave.LeaveID)
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
	WHERE UserID = @UserID AND YEAR(DateStart) = @Year
GO
/* Create procedure view subordinate detail *************************************************/
CREATE PROCEDURE sp_LogDetail
	@UserID INT
AS
SELECT Time, Username, [Action]
	FROM LogDetail
	WHERE UserID = @UserID
UNION
SELECT Time, Username, [Action]
	FROM LogDetail
	WHERE LeaveID IN (SELECT LeaveID FROM Leave WHERE UserID = @UserID)
ORDER BY Time
GO
/* Create procedure view subordinate detail *********************************************************/
CREATE PROCEDURE sp_SubordinateDetail
	@SuperiorID INT
AS
SELECT * 
	FROM SubordinateDetail
	WHERE SuperiorID = @SuperiorID
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
		@Action,
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
INSERT INTO Leave
	VALUES(
		@UserID,
		@DateStart,
		@DateEnd,
		'Not Approved',	
		@Reason,
		@Communication,
		@Date,
		@Subject
	)
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
/* Add records ******************************************************************************/
INSERT INTO Position VALUES('Engineer',15)
INSERT INTO Position VALUES('Manager',16)
INSERT INTO Position VALUES('Business Manager',17)
INSERT INTO Position VALUES('Managing Director',18)
--pass 12345
INSERT INTO [User] VALUES('tuannt','827ccb0eea8a706c4c34a16891f84e7b','Tuan Nguyen Trung',1,4)
INSERT INTO [User] VALUES('tuanlm','827ccb0eea8a706c4c34a16891f84e7b','Tuan Luu Minh',1,3)
INSERT INTO [User] VALUES('hoanpm','827ccb0eea8a706c4c34a16891f84e7b','Hoan Pham Minh',1,3)
INSERT INTO [User] VALUES('haidd','827ccb0eea8a706c4c34a16891f84e7b','Hai Dang Dinh',2,2)	
INSERT INTO [User] VALUES('tamtth','827ccb0eea8a706c4c34a16891f84e7b','Tam Tong Thi Hao',2,2)
INSERT INTO [User] VALUES('thulth','827ccb0eea8a706c4c34a16891f84e7b','Thu Le Thi Hoai',3,2)
INSERT INTO [User] VALUES('truongdd','827ccb0eea8a706c4c34a16891f84e7b','Truong Dinh Duc',3,2)
INSERT INTO [User] VALUES('nhungttk','827ccb0eea8a706c4c34a16891f84e7b','Nhung Tran Thi Kim',4,1)
INSERT INTO [User] VALUES('lampx','827ccb0eea8a706c4c34a16891f84e7b','Lam Pham Xuan',4,1)	
INSERT INTO [User] VALUES('vudp','827ccb0eea8a706c4c34a16891f84e7b','Vu Dao Phan',5,1)
INSERT INTO [User] VALUES('huongnt','827ccb0eea8a706c4c34a16891f84e7b','Huong Nguyen Thanh',5,1)	
INSERT INTO [User] VALUES('mainq','827ccb0eea8a706c4c34a16891f84e7b','Mai Nuyen Quynh',6,1)
INSERT INTO [User] VALUES('ngocttm','827ccb0eea8a706c4c34a16891f84e7b','Ngoc Tong Thi Minh',6,1)
INSERT INTO [User] VALUES('huongctt','827ccb0eea8a706c4c34a16891f84e7b','Huong Cao Thi Thu',7,1)
INSERT INTO [User] VALUES('diepttm','827ccb0eea8a706c4c34a16891f84e7b','Diep Tran Thi My',7,1)