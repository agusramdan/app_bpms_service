package com.agus.ramdan.bmps.dto;

import com.agus.ramdan.bmps.domain.QRCode;
import com.agus.ramdan.bmps.machine.domain.TrxDepositMachine;
import com.agus.ramdan.bmps.machine.domain.TrxDepositMachineDenomination;
import com.agus.ramdan.bmps.machine.dto.*;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface QRCodeMapper {
    QRCode fromQRCodeRequest(QRCodeRequest request);
    QRCodeResponse toQRCodeResponse(QRCode qrCode);

}
