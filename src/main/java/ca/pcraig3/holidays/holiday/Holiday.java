package ca.pcraig3.holidays.holiday;

import ca.pcraig3.holidays.province.Province;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joestelmach.natty.Parser;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
@Table(name="HOLIDAY")
public class Holiday {

    @Id
    @Column(name = "ID", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name="DATE_STRING")
    private String date;

    @NotNull
    @Column(name="NAME_EN")
    private String nameEn;

    @NotNull
    @Column(name="NAME_FR")
    private String nameFr;

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "HOLIDAY_PROVINCE",
            joinColumns = @JoinColumn(name = "HOLIDAY_ID"),
            inverseJoinColumns = @JoinColumn(name = "PROVINCE_ID")
    )
    @JsonIgnoreProperties({ "holidays" , "nextHoliday" })
    @OrderBy
    private final Set<Province> provinces = new HashSet<>();

    @Formula("(SELECT COUNT(*) FROM HOLIDAY_PROVINCE hp WHERE hp.HOLIDAY_ID = ID) = 13")
    private boolean isNational;

    // isRelative

    protected Holiday () {}

    Holiday(@NotNull String date, @NotNull String nameEn, @NotNull String nameFr) {
        this.date = date;
        this.nameEn = nameEn;
        this.nameFr = nameFr;
    }

    public static Date str2Date(String dateString) {
        return new Parser().parse(dateString).get(0).getDates().get(0);
    }

    public Long getId() {
        return id;
    }

    public String getDate() {

        String _date = (this.date.equals("Monday on or before May 24")) ? "Monday before May 25" : this.date;

        Date firstDate = str2Date(_date);
        // iso format : "yyyy-MM-dd'T'HH:mm:ss.SSSZ" (ie, 2019-01-01T00:00:00.000)
        // spoken english format : "EEEE, MMMM d" (ie, Tuesday, January 1)
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMMM d", Locale.CANADA);

        return formatter.format(firstDate);
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getNameFr() {
        return nameFr;
    }

    public Set<Province> getProvinces() {
        return provinces;
    }

    public boolean getNational() { return isNational; }

    @Override
    public String toString() {
        return "Holiday{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", nameEn='" + nameEn + '\'' +
                ", nameFr='" + nameFr + '\'' +
                ", provinces=" + provinces.size() +
                ", isNational=" + isNational +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Holiday holiday = (Holiday) o;
        return Objects.equals(id, holiday.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
