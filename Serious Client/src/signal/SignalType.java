package signal;

public enum SignalType {
	Invalid,
	LoginRequest,
	LoginAuthenticated,
	Logout,
	BroadcastLogin,
	Message,
	AcknowledgeMessage,
	UserTyping,
	ChangeFont,
	ChangePassword,
	PasswordChanged,
	AddContact,
	ContactAdded,
	DeleteContact,
	ContactDeleted,
	BlockContact,
	ContactBlocked,
	ChangeNickname,
	ChangePersonalMessage,
	ChangeStatus
}
