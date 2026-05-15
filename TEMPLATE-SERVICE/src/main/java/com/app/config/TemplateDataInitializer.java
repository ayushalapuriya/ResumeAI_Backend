package com.app.config;

import com.app.entity.ResumeTemplate;
import com.app.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class TemplateDataInitializer implements CommandLineRunner {

    private final TemplateRepository repository;

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() <= 2) {
            // Demo 1: Modern Professional (Enhanced)
            repository.save(ResumeTemplate.builder()
                    .name("Modern Professional")
                    .description("Clean, minimalist layout for corporate roles.")
                    .category("PROFESSIONAL")
                    .isPremium(false)
                    .isActive(true)
                    .usageCount(120)
                    .htmlLayout("<div class=\"resume-modern\">\n  <header>\n    <h1>{{fullName}}</h1>\n    <p class=\"subtitle\">{{jobTitle}}</p>\n    <p class=\"contact\">{{email}} | {{phone}} | {{location}}</p>\n  </header>\n  <section>\n    <h2 class=\"section-title\">Executive Summary</h2>\n    <p>{{summary}}</p>\n  </section>\n  <section>\n    <h2 class=\"section-title\">Experience</h2>\n    {{#experience}}\n    <div class=\"job\">\n      <div class=\"job-header\">\n        <strong>{{role}}</strong>\n        <span>{{startDate}} - {{endDate}}</span>\n      </div>\n      <div class=\"company\">{{company}}</div>\n      <p class=\"desc\">{{description}}</p>\n    </div>\n    {{/experience}}\n  </section>\n  <section>\n    <h2 class=\"section-title\">Skills</h2>\n    <div class=\"skills-grid\">{{#skills}}<span class=\"skill-tag\">{{.}}</span>{{/skills}}</div>\n  </section>\n</div>")
                    .cssStyles(".resume-modern { font-family: 'Inter', sans-serif; padding: 40px; color: #1e293b; line-height: 1.5; }\nheader { text-align: center; border-bottom: 2px solid #e2e8f0; padding-bottom: 20px; }\nh1 { margin: 0; font-size: 28px; font-weight: 800; color: #0f172a; }\n.subtitle { font-size: 18px; color: #6366f1; font-weight: 600; margin: 5px 0; }\n.contact { font-size: 12px; color: #64748b; }\n.section-title { font-size: 14px; text-transform: uppercase; letter-spacing: 1px; color: #6366f1; border-bottom: 1px solid #e2e8f0; padding-bottom: 5px; margin-top: 30px; }\n.job { margin-top: 15px; }\n.job-header { display: flex; justify-content: space-between; font-size: 14px; }\n.company { color: #64748b; font-style: italic; font-size: 13px; }\n.desc { font-size: 13px; margin-top: 5px; }\n.skills-grid { display: flex; flex-wrap: wrap; gap: 8px; margin-top: 10px; }\n.skill-tag { background: #f1f5f9; padding: 4px 10px; border-radius: 4px; font-size: 12px; }")
                    .createdAt(LocalDate.now())
                    .build());

            // Demo 2: ATS-Friendly Professional
            repository.save(ResumeTemplate.builder()
                    .name("ATS-Friendly")
                    .description("Optimized for applicant tracking systems with a clean hierarchy.")
                    .category("ATS-OPTIMISED")
                    .isPremium(false)
                    .isActive(true)
                    .usageCount(250)
                    .htmlLayout("<div class=\"ats-resume\">\n  <div class=\"header\">\n    <h1>{{fullName}}</h1>\n    <p>{{location}} | {{phone}} | {{email}}</p>\n  </div>\n  \n  <div class=\"section\">\n    <div class=\"section-title\">Professional Summary</div>\n    <p>{{summary}}</p>\n  </div>\n\n  <div class=\"section\">\n    <div class=\"section-title\">Experience</div>\n    {{#experience}}\n    <div class=\"job\">\n      <div class=\"row\"><strong>{{role}}</strong><span>{{startDate}} - {{endDate}}</span></div>\n      <div class=\"company\">{{company}}</div>\n      <p>{{description}}</p>\n    </div>\n    {{/experience}}\n  </div>\n\n  <div class=\"section\">\n    <div class=\"section-title\">Skills</div>\n    <p>{{#skills}}{{.}}, {{/skills}}</p>\n  </div>\n</div>")
                    .cssStyles(".ats-resume { font-family: 'Times New Roman', serif; padding: 50px; color: #000; line-height: 1.4; }\n.header { text-align: center; border-bottom: 2px solid #000; padding-bottom: 10px; margin-bottom: 20px; }\nh1 { margin: 0; font-size: 24pt; }\n.section { margin-bottom: 15px; }\n.section-title { font-weight: bold; text-transform: uppercase; border-bottom: 1px solid #000; margin-bottom: 5px; }\n.row { display: flex; justify-content: space-between; }\n.company { font-style: italic; margin-bottom: 4px; }\np { margin: 5px 0; font-size: 11pt; }")
                    .createdAt(LocalDate.now())
                    .build());

            // Demo 3: Modern Executive
            repository.save(ResumeTemplate.builder()
                    .name("Modern Executive")
                    .description("Strong visual impact for leadership and high-level roles.")
                    .category("MODERN")
                    .isPremium(true)
                    .isActive(true)
                    .usageCount(45)
                    .htmlLayout("<div class=\"executive-resume\">\n  <div class=\"top-bar\"></div>\n  <div class=\"content\">\n    <div class=\"header-row\">\n      <div class=\"name-block\">\n        <h1>{{fullName}}</h1>\n        <p class=\"tagline\">{{jobTitle}}</p>\n      </div>\n      <div class=\"contact-block\">\n        <p>{{email}}</p><p>{{phone}}</p><p>{{location}}</p>\n      </div>\n    </div>\n    \n    <div class=\"main-layout\">\n      <div class=\"left-col\">\n        <h3>Experience</h3>\n        {{#experience}}\n        <div class=\"exp-box\">\n          <h4>{{role}}</h4>\n          <div class=\"meta\">{{company}} | {{startDate}} \u2014 {{endDate}}</div>\n          <p>{{description}}</p>\n        </div>\n        {{/experience}}\n      </div>\n      <div class=\"right-col\">\n        <h3>Expertise</h3>\n        <ul class=\"skill-list\">{{#skills}}<li>{{.}}</li>{{/skills}}</ul>\n        <h3>Education</h3>\n        {{#education}}<div class=\"edu-box\"><strong>{{degree}}</strong><p>{{institution}}</p></div>{{/education}}\n      </div>\n    </div>\n  </div>\n</div>")
                    .cssStyles(".executive-resume { font-family: 'Inter', sans-serif; background: #fff; color: #333; min-height: 297mm; }\n.top-bar { height: 12px; background: #1e293b; }\n.content { padding: 40px; }\n.header-row { display: flex; justify-content: space-between; border-bottom: 1px solid #e2e8f0; padding-bottom: 20px; }\nh1 { margin: 0; font-size: 32px; font-weight: 900; color: #1e293b; }\n.tagline { color: #6366f1; font-weight: 700; text-transform: uppercase; letter-spacing: 1px; margin-top: 5px; }\n.contact-block p { margin: 2px 0; font-size: 11px; text-align: right; color: #64748b; }\n.main-layout { display: grid; grid-template-columns: 2fr 1fr; gap: 40px; margin-top: 30px; }\nh3 { text-transform: uppercase; font-size: 13px; border-left: 4px solid #1e293b; padding-left: 10px; color: #1e293b; margin-bottom: 15px; }\n.exp-box { margin-bottom: 20px; }\n.exp-box h4 { margin: 0; font-size: 15px; color: #0f172a; }\n.meta { font-size: 11px; color: #6366f1; margin: 4px 0 8px; font-weight: 600; }\n.exp-box p { font-size: 12px; line-height: 1.6; color: #475569; }\n.skill-list { list-style: none; padding: 0; display: flex; flex-wrap: wrap; gap: 6px; }\n.skill-list li { background: #f1f5f9; padding: 4px 10px; font-size: 11px; border-radius: 4px; color: #475569; font-weight: 500; }\n.edu-box { margin-bottom: 15px; font-size: 12px; }")
                    .createdAt(LocalDate.now())
                    .build());
            
            // Demo 4: Creative Modern
            repository.save(ResumeTemplate.builder()
                    .name("Creative Modern")
                    .description("Bold accents and a modern layout for creative professionals.")
                    .category("CREATIVE")
                    .isPremium(true)
                    .isActive(true)
                    .usageCount(85)
                    .htmlLayout("<div class=\"creative-resume\">\n  <div class=\"sidebar\">\n    <div class=\"initial-circle\">{{fullName.0}}</div>\n    <h1>{{fullName}}</h1>\n    <p class=\"job-title\">{{jobTitle}}</p>\n    \n    <div class=\"side-section\">\n      <h3>Contact</h3>\n      <p>{{email}}</p>\n      <p>{{phone}}</p>\n      <p>{{location}}</p>\n    </div>\n\n    <div class=\"side-section\">\n      <h3>Skills</h3>\n      <div class=\"skills-cloud\">\n        {{#skills}}<span>{{.}}</span>{{/skills}}\n      </div>\n    </div>\n  </div>\n  \n  <div class=\"main-content\">\n    <section>\n      <h2>About Me</h2>\n      <p>{{summary}}</p>\n    </section>\n    \n    <section>\n      <h2>Experience</h2>\n      {{#experience}}\n      <div class=\"exp-item\">\n        <div class=\"exp-header\">\n          <strong>{{role}}</strong>\n          <span>{{startDate}} - {{endDate}}</span>\n        </div>\n        <div class=\"company\">{{company}}</div>\n        <p>{{description}}</p>\n      </div>\n      {{/experience}}\n    </section>\n  </div>\n</div>")
                    .cssStyles(".creative-resume { display: flex; font-family: 'Poppins', sans-serif; min-height: 100%; }\n.sidebar { width: 35%; background: #1a1a2e; color: white; padding: 40px 20px; }\n.initial-circle { width: 60px; height: 60px; background: #6366f1; border-radius: 50%; display: flex; align-items: center; justify-content: center; font-size: 24px; font-weight: bold; margin-bottom: 20px; }\n.sidebar h1 { font-size: 22px; margin: 0; }\n.job-title { color: #6366f1; font-size: 14px; margin-bottom: 30px; }\n.side-section { margin-bottom: 25px; }\n.side-section h3 { font-size: 12px; text-transform: uppercase; letter-spacing: 1px; border-bottom: 1px solid #ffffff33; padding-bottom: 5px; margin-bottom: 10px; }\n.side-section p { font-size: 11px; margin: 4px 0; color: #cbd5e1; }\n.skills-cloud { display: flex; flex-wrap: wrap; gap: 5px; }\n.skills-cloud span { background: #6366f133; color: #6366f1; padding: 2px 8px; border-radius: 4px; font-size: 10px; }\n.main-content { flex: 1; padding: 40px; background: #fff; }\n.main-content h2 { font-size: 18px; color: #1a1a2e; border-bottom: 2px solid #f1f5f9; padding-bottom: 8px; margin-bottom: 15px; }\n.exp-item { margin-bottom: 20px; }\n.exp-header { display: flex; justify-content: space-between; font-size: 14px; font-weight: 600; }\n.company { color: #6366f1; font-size: 13px; font-style: italic; }\n.main-content p { font-size: 13px; color: #475569; line-height: 1.6; }")
                    .createdAt(LocalDate.now())
                    .build());

            System.out.println("✅ Real-world demo templates saved to database.");
        }
    }
}
