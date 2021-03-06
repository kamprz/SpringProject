package wat.semestr8.tim.dtos;

import lombok.Getter;
import lombok.Setter;
import wat.semestr8.tim.dtos.validators.date.ReadableDateConstraint;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class ConcertDto
{
    @NotNull
    private int idConcert;
    @NotBlank
    private String concertTitle;
    @ReadableDateConstraint
    private String date;

    private BigDecimal additionalOrganisationCosts;
    private boolean isApproved;
    @NotNull
    private BigDecimal ticketCost;
    private String concertRoomName;
    private String concertRoomAddress;
    @NotBlank
    private String concertPerformers;
    @NotEmpty
    private List<PieceOfMusicDto> repertoire = new LinkedList<>();
    public void addPieceOfMusic(PieceOfMusicDto p){ repertoire.add(p);}
}