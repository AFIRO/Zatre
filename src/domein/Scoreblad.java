package domein;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Scoreblad {
    private final List<ScorebladRegel> regels;

    /**
     * UC3: constructor van Scoreblad
     */
    public Scoreblad() {
        this.regels = new ArrayList<>();
    }

    /**
     * UC3: geeft een lijst van String weergaves van de individuele regels terug voor gebruik in GUI.
     *
     * @return de lijst aan string weergaves.
     */

    public List<String> getRegels() {
        return regels.stream()
                .map(ScorebladRegel::toString)
                .collect(Collectors.toList());
    }

    /**
     * UC3: Voegt een ScorebladRegel object toe aan de lijst regels.
     * Genereert bonuspunten op basis van aantal regel op scoreblad.
     *
     * @param dubbeleScore boolean die bijhoudt of in die beurt de score moet verdubbeld worden omdat de steen op een wit vak stond.
     */
    private void voegLegeRegelToeAanScoreblad(boolean dubbeleScore) {
        int BonusPunten = 0;

        if (this.regels.size() < 4)
            BonusPunten = 3;

        if (this.regels.size() >= 4 && this.regels.size() < 8)
            BonusPunten = 4;

        if (this.regels.size() >= 8 && this.regels.size() < 12)
            BonusPunten = 5;

        if (this.regels.size() >= 12)
            BonusPunten = 6;

        regels.add(new ScorebladRegel(dubbeleScore, BonusPunten));
    }

    /**
     * UC3: gebruikt stream om per opgeslagen ScorebladRegel uit lijst regels de punten op te vragen en telt deze dan op.
     * Hierdoor wordt de uiteindelijke som berekend.
     *
     * @return int score
     */
    public int berekenScoreVanScoreblad() {
        return regels.stream()
                .map(ScorebladRegel::getScoreVoorRegel)
                .reduce(0, Integer::sum);
    }

    /**
     * UC4: Genereer een regel met de scores voor deze ronde.
     *
     * @param puntenArraysVoorAlleZetten array list die de scores bevat van de ronde
     */

    public void voegRegelsToeAanScoreblad(ArrayList<String> puntenArraysVoorAlleZetten) {
        int dubbleScoreCounter = 0;
        //controleer of de laatse regel in de scoreblad een dubbele score regel die werd genereerd vanuit een vorige dubbele score regel.
        if (!regels.isEmpty() && regels.get(regels.size() - 1).isDubbeleScore() && regels.get(regels.size() - 1).getScoreVoorRegel() == 0) {
            // Deze wordt dan gebruikt voor deze ronde
            ScorebladRegel vorigeRegelMetDubbeleBonus = regels.get(regels.size() - 1);
            //ga door alle scores uit deze ronde en pas die regel aan
            for (String zet : puntenArraysVoorAlleZetten) {
                VoegExtraKruisjesToeAanRegel(vorigeRegelMetDubbeleBonus, zet);
            }
        } else {
            //controleer in de huidige zetten of we een dubbele score ronde hebben
            boolean isDubbeleScoreRegel = false;
            //controleer of er meerdere dubbele scores zijn verdiend.
            for (String zet : puntenArraysVoorAlleZetten) {
                if (zet.charAt(0) == '1') {
                    isDubbeleScoreRegel = true;
                    dubbleScoreCounter++;
                }
            }
            //maak de nieuwe regel aan met correcte dubbele score boost
            voegLegeRegelToeAanScoreblad(isDubbeleScoreRegel);
            ScorebladRegel actieveRegel = regels.get(regels.size() - 1);
            //pas deze nieuwe regel aan met de gevonden scores
            for (String zet : puntenArraysVoorAlleZetten) {
                VoegExtraKruisjesToeAanRegel(actieveRegel, zet);
            }
        }
        //genereer een extra dubbele score regel als bonus
        if (dubbleScoreCounter > 1)
            voegLegeRegelToeAanScoreblad(true);

    }

    /**
     * UC4: Pas de actieve ronde regel aan met kruisjes uit een volgende zet.
     *
     * @param aanTePassenRegel de aan te passen regel
     * @param zet              string die de resultaten van de zet bevat
     */

    private void VoegExtraKruisjesToeAanRegel(ScorebladRegel aanTePassenRegel, String zet) {
        //eerste twee tekens die de dubbele score boolean symboliseren worden eruit gehaald
        String zetMetEnkelScores = zet.substring(2);
        //scores worden omgezet tot array
        String[] scores = zetMetEnkelScores.split(",");

        //elke score wordt doorgegeven aan de regel voor aanpassing
        for (String score : scores) {
            aanTePassenRegel.pasRegelAanMetVerdereScores(Integer.parseInt(score));
        }

    }

    /**
     * UC3: String override voor scoreblad
     */

    @Override
    public String toString() {
        return regels.stream()
                .map(ScorebladRegel::toString)
                .collect(Collectors.joining("\n"));
    }
}
