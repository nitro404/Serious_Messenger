package signal;

import shared.ByteStream;

public class Signal {
	
	protected SignalType m_signalType;
	
	public Signal(SignalType signalType) {
		m_signalType = signalType;
	}
	
	public SignalType getSignalType() {
		return m_signalType;
	}
	
	public int getSignalID() {
		return getSignalID(m_signalType);
	}
	
	public static int getSignalID(SignalType signalType) {
		switch(signalType) {
			case LoginRequest:
				return 0;
			case LoginAuthenticated:
				return 1;
			case Logout:
				return 2;
			case BroadcastLogin:
				return 3;
			case Message:
				return 4;
			case AcknowledgeMessage:
				return 5;
			case UserTyping:
				return 6;
			case ChangeFont:
				return 7;
			case ChangePassword:
				return 8;
			case PasswordChanged:
				return 9;
			case AddContact:
				return 10;
			case DeleteContact:
				return 11;
			case ContactDeleted:
				return 12;
			case BlockContact:
				return 13;
			case ContactBlocked:
				return 14;
			case ChangeNickname:
				return 15;
			case ChangePersonalMessage:
				return 16;
			case ChangeStatus:
				return 17;
		}
		return -1;
	}
	
	public static SignalType getSignalType(int id) {
		switch(id) {
			case 0:
				return SignalType.LoginRequest;
			case 1:
				return SignalType.LoginAuthenticated;
			case 2:
				return SignalType.Logout;
			case 3:
				return SignalType.BroadcastLogin;
			case 4:
				return SignalType.Message;
			case 5:
				return SignalType.AcknowledgeMessage;
			case 6:
				return SignalType.UserTyping;
			case 7:
				return SignalType.ChangeFont;
			case 8:
				return SignalType.ChangePassword;
			case 9:
				return SignalType.PasswordChanged;
			case 10:
				return SignalType.AddContact;
			case 11:
				return SignalType.DeleteContact;
			case 12:
				return SignalType.ContactDeleted;
			case 13:
				return SignalType.BlockContact;
			case 14:
				return SignalType.ContactBlocked;
			case 15:
				return SignalType.ChangeNickname;
			case 16:
				return SignalType.ChangePersonalMessage;
			case 17:
				return SignalType.ChangeStatus;
		}
		return SignalType.Invalid;
	}
	
	public long checksum() {
		return ByteStream.getChecksum(getSignalID());
	}
	
	public static Signal readFrom(ByteStream byteStream) {
		if(byteStream == null) { return null; }
		
		int id = byteStream.nextInteger();
		long idChecksum = byteStream.nextLong();
		if(ByteStream.getChecksum(id) != idChecksum) {
			return new Signal(SignalType.Invalid);
		}
		else {
			return new Signal(getSignalType(id));
		}
	}
	
	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		int id = getSignalID();
		byteStream.addInteger(id);
		byteStream.addLong(ByteStream.getChecksum(id));
	}
	
}
