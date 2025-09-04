// Product Interfaces (These will remain the same after refactoring)
interface ReportHeader {
    void render();
}

interface ReportBody {
    void render();
}

interface ReportFooter {
    void render();
}

// Concrete Products (Directly instantiated in the problematic code)
class PdfHeader implements ReportHeader {
    @Override
    public void render() { System.out.println("Rendering PDF Header..."); }
}

class PdfBody implements ReportBody {
    @Override
    public void render() { System.out.println("Rendering PDF Body..."); }
}

class PdfFooter implements ReportFooter {
    @Override
    public void render() { System.out.println("Rendering PDF Footer..."); }
}

class HtmlHeader implements ReportHeader {
    @Override
    public void render() { System.out.println("Rendering HTML Header..."); }
}

class HtmlBody implements ReportBody {
    @Override
    public void render() { System.out.println("Rendering HTML Body..."); }
}

class HtmlFooter implements ReportFooter {
    @Override
    public void render() { System.out.println("Rendering HTML Footer..."); }
}

abstract class ReportFactory{
    public abstract ReportHeader createHeader();
    public abstract ReportBody createBody();
    public abstract ReportFooter createFooter();
}

class pdfReportFactory extends ReportFactory {
    @Override
    public ReportHeader createHeader() {
        return new PdfHeader();
    }

    @Override
    public ReportBody createBody(){
        return new PdfBody();
    }

    @Override
    public ReportFooter createFooter(){
        return new PdfFooter();
    }

}
class HTMLReportFactory extends ReportFactory {
    @Override
    public ReportHeader createHeader() {
        return new HtmlHeader();
    }

    @Override
    public ReportBody createBody(){
        return new HtmlBody();
    }

    @Override
    public ReportFooter createFooter(){
        return new HtmlFooter();
    }

}

// Problematic Client (Needs refactoring to use Abstract Factory)
class ReportGenerator {
    private ReportHeader header;
    private ReportBody body;
    private ReportFooter footer;

    public ReportGenerator(ReportFactory report) {
        this.header = report.createHeader();
        this.body = report.createBody();
        this.footer = report.createFooter();
    }

    public void generateReport() {
        System.out.println("--- Generating Report ---");
        header.render();
        body.render();
        footer.render();
        System.out.println("--- Report Generated ---");
        System.out.println();
    }
}

// Main class to demonstrate the problem
public class test {
    public static void main(String[] args) {
        System.out.println("Creating PDF Report:");
        ReportGenerator pdfReport = new ReportGenerator(new pdfReportFactory());
        pdfReport.generateReport();

        System.out.println("Creating HTML Report:");
        ReportGenerator htmlReport = new ReportGenerator(new HTMLReportFactory());
        htmlReport.generateReport();

        // Problem: If you want to add a new report type (e.g., XML Report),
        // you have to modify the ReportGenerator constructor.
        // This violates the Open/Closed Principle.
        // Also, the ReportGenerator is tightly coupled to concrete product classes.
        System.out.println("Problem: ReportGenerator is tightly coupled to concrete report elements.");
        System.out.println("Adding a new report type requires modifying the ReportGenerator class.");
    }
}