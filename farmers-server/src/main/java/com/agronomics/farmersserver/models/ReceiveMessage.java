package com.agronomics.farmersserver.models;

import lombok.Getter;
import lombok.Setter;

public class ReceiveMessage {

	private @Getter @Setter String messagetype;
	private @Getter @Setter Long farmid;
	private @Getter @Setter Long dealid;
}
